package com.projectreap.ProjectReap.service;

import com.projectreap.ProjectReap.entity.Appreciation;
import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Badge;
import com.projectreap.ProjectReap.pojo.AppreciatedData;
import com.projectreap.ProjectReap.repository.AppreciationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AppreciationService {

    @Autowired
    AppreciationRepository appreciationRepository;

    @Autowired
    UserService userService;




    public Appreciation save(AppreciatedData appreciatedData,HttpServletRequest request) {
        Appreciation appreciation=appreciatedDataToDomain(appreciatedData,request);
        // handlingBadge();
        return appreciationRepository.save(appreciation);
    }

//    @Transactional
//    public String handlingBadge(AppreciatedData appreciatedData, User user){
//        if(appreciatedData.getBadge().equals(user.getBadge().getGoldBadge())){
//            //Integer gol
//        }
//    }

    public Appreciation appreciatedDataToDomain(AppreciatedData appreciatedData,HttpServletRequest request) {
        Appreciation appreciation = new Appreciation();
        User currentUser = (User) request.getSession().getAttribute("user");

        User userId = userService.getById(currentUser.getId());
        appreciation.setAppreciatedBy(userId);
        System.out.println("user ID"+userId);
        System.out.println("currentUserId"+currentUser.getId());


        User appreciatedUser = userService.getById(appreciatedData.getAppreciatedUser());
        appreciation.setAppreciatedUser(appreciatedUser);

//        Badge.getValue(appreciatedData.getBadge());
        Badge badge=Badge.valueOf(appreciatedData.getBadge());
        //appreciatedData.getBadge().
        appreciation.setBadge(badge);


        appreciation.setKarma(appreciatedData.getKarma());
        appreciation.setKarmaReason(appreciatedData.getKarmaReason());
        return appreciation;
    }

}
