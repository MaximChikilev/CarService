package com.example.carservice.mail;

import com.example.carservice.dto.ClientCarRecommendedToServiceDTO;
import org.springframework.stereotype.Component;

@Component
public class ProposalMessageGenerator extends AbstractMessageGenerator<ClientCarRecommendedToServiceDTO>{

    @Override
    protected String getText(ClientCarRecommendedToServiceDTO element) {
        return  String.format("Dear %s, It's time for your vehicle %s to get serviced. Next inspection is %s",
                element.getClientCarAVGMileageDTO().getFirstName(),
                element.getClientCarAVGMileageDTO().getLicensePlateNumber(),
                element.getInspectionName());
    }

    @Override
    protected String getSubject() {
        return "Car Service. Maintenance time is approaching";
    }

    @Override
    protected String getTo(ClientCarRecommendedToServiceDTO element) {
        return element.getClientCarAVGMileageDTO().getEmail();
    }

}
