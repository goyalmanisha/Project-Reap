package com.projectreap.ProjectReap.service;

import com.projectreap.ProjectReap.entity.Appreciation;
import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Badge;
import com.projectreap.ProjectReap.pojo.AppreciatedData;
import com.projectreap.ProjectReap.repository.AppreciationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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

    public Appreciation save(AppreciatedData appreciatedData,HttpServletRequest request) {
        Appreciation appreciation=appreciatedDataToDomainAndUpdatingBadge(appreciatedData,request);
        // handlingBadge();
        return appreciationRepository.save(appreciation);
    }


    public Map<String, Integer> handlingBadge(User user){
        List<Appreciation> appreciations = appreciationRepository.findAllByAppreciatedUser(user);
        System.out.println(appreciations);
        //List<String> appreciationBadgeList = appreciationRepository.findAllByBadge(appreciations);
        Map<String, Integer> badgeCount = new HashMap();
        appreciations.forEach(appreciation-> {
            if(badgeCount.get(appreciation.getBadge().name()) == null) {
                badgeCount.put(appreciation.getBadge().name(), 1);
            }else {
                badgeCount.put(appreciation.getBadge().name(), badgeCount.get(appreciation.getBadge().name()) + 1);
            }
        });
        System.out.println(badgeCount);
        return badgeCount;
    }

    public Appreciation appreciatedDataToDomainAndUpdatingBadge(AppreciatedData appreciatedData, HttpServletRequest request) {
        Appreciation appreciation = new Appreciation();
        User currentUser = (User) request.getSession().getAttribute("user");

        User appreciatedBy = userService.getById(currentUser.getId());
        appreciation.setAppreciatedBy(appreciatedBy);

        User appreciatedUser = userService.getById(appreciatedData.getAppreciatedUser());
        appreciation.setAppreciatedUser(appreciatedUser);

        Badge badge=Badge.valueOf(appreciatedData.getBadge());
        appreciation.setBadge(badge);

        if(Objects.nonNull(appreciation.getBadge()) && Badge.gold.equals(appreciation.getBadge())){
            appreciatedBy.getBadge().setGoldBadge(appreciatedBy.getBadge().getGoldBadge()-1);
            //userService.save(currentUser);
            userService.save(appreciatedBy);

        }
        else if(appreciation.getBadge().equals(Badge.silver)){
            appreciatedBy.getBadge().setSilverBadge(appreciatedBy.getBadge().getSilverBadge()-1);
            userService.save(appreciatedBy);
        }
        else {
            appreciatedBy.getBadge().setBronzeBadge(appreciatedBy.getBadge().getBronzeBadge()-1);
            userService.save(appreciatedBy);
        }
       // userService.save(currentUser);

        appreciation.setKarma(appreciatedData.getKarma());
        appreciation.setKarmaReason(appreciatedData.getKarmaReason());
        return appreciation;
    }

}
