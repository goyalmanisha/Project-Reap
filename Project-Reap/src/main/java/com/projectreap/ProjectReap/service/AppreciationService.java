package com.projectreap.ProjectReap.service;

import com.projectreap.ProjectReap.entity.Appreciation;
import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Badge;
import com.projectreap.ProjectReap.pojo.AppreciatedData;
import com.projectreap.ProjectReap.repository.AppreciationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AppreciationService {

    @Autowired
    AppreciationRepository appreciationRepository;

    @Autowired
    UserService userService;

    public Appreciation save(AppreciatedData appreciatedData, HttpServletRequest request) {
        Appreciation appreciation = appreciatedDataToDomainAndUpdatingBadge(appreciatedData, request);
        return appreciationRepository.save(appreciation);
    }

    public List<Appreciation> findAllAppreciatedUser(User user){
        return appreciationRepository.findAllByAppreciatedUser(user);
    }

    public Map<String, Integer> handlingBadge(User user) {
        List<Appreciation> appreciations = appreciationRepository.findAllByAppreciatedUser(user);
        Map<String, Integer> badgeCount = new HashMap();
        for (Badge badge: Badge.values()) {
            badgeCount.put(badge.name(), 0);
        }
        appreciations.forEach(appreciation -> {
            if (badgeCount.get(appreciation.getBadge().name()) == null) {
                badgeCount.put(appreciation.getBadge().name(), 1);
            } else {
                badgeCount.put(appreciation.getBadge().name(), badgeCount.get(appreciation.getBadge().name()) + 1);
            }
        });
        return badgeCount;
    }

//    public Integer BadgesReceived(User user){
//        // using lambda expression
//        handlingBadge(user).keySet()
//	        .iterator()
//	                .forEachRemaining(key -> System.out.println(key + "=" + handlingBadge(user).get(key)));
//    }



    public Integer getTotalPoints(User user) {
        Map<String, Integer> badgePoints = handlingBadge(user);

        Integer totalpoints = ((badgePoints.get("gold") * Badge.gold.getWeight()) + (badgePoints.get("silver") * Badge.silver.getWeight()) + (badgePoints.get("bronze") * Badge.silver.getWeight()));
        return totalpoints;
    }

    public Appreciation appreciatedDataToDomainAndUpdatingBadge(AppreciatedData appreciatedData, HttpServletRequest request) {
        Appreciation appreciation = new Appreciation();
        User currentUser = (User) request.getSession().getAttribute("user");

        User appreciatedBy = userService.getById(currentUser.getId());
        appreciation.setAppreciatedBy(appreciatedBy);

        User appreciatedUser = userService.getById(appreciatedData.getAppreciatedUser());
        appreciation.setAppreciatedUser(appreciatedUser);

        Badge badge = Badge.valueOf(appreciatedData.getBadge());
        appreciation.setBadge(badge);

        if (Objects.nonNull(appreciation.getBadge()) && Badge.gold.equals(appreciation.getBadge())) {
            appreciatedBy.getBadge().setGoldBadge(appreciatedBy.getBadge().getGoldBadge() - 1);
            //userService.save(currentUser);
            userService.save(appreciatedBy);

        } else if (Objects.nonNull(appreciation.getBadge()) && Badge.silver.equals(appreciation.getBadge())) {
            appreciatedBy.getBadge().setSilverBadge(appreciatedBy.getBadge().getSilverBadge() - 1);
            userService.save(appreciatedBy);
        } else {
            appreciatedBy.getBadge().setBronzeBadge(appreciatedBy.getBadge().getBronzeBadge() - 1);
            userService.save(appreciatedBy);
        }
        appreciation.setKarma(appreciatedData.getKarma());
        System.out.println("appreciated data is********" + appreciatedData.getReason());
        appreciation.setReason(appreciatedData.getReason());

        System.out.println("appreciation details" + appreciation);
        return appreciation;
    }

}
