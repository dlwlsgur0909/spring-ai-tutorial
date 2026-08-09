package spring.ai.tutorial.dto;

public record UserResponse(String name,
                           Long age,
                           String address,
                           String phoneNumber,
                           String zipCode) {

}
