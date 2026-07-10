package spring.ai.tutorial.dto;

public record UserResponseDto(String name,
                              Long age,
                              String address,
                              String phoneNumber,
                              String zipCode) {

}
