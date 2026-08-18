package com.reports.quality_assurance.controller.dto;

import lombok.Getter;

/**
 * DTO class handling incoming requests to create new Quality Assurance Engineer.
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@Getter
public class RegisterQualityAssuranceEngineerDTO {

    private String firstName;
    private String lastName;
    private String username;

    private static final int MINIMUM_NAME_LENGTH = 2;
    private static final int MAXIMUM_NAME_LENGTH = 30;

    private static final int MINIMUM_USERNAME_LENGTH = 4;
    private static final int MAXIMUM_USERNAME_LENGTH = 10;

    public RegisterQualityAssuranceEngineerDTO(){
    }

    public RegisterQualityAssuranceEngineerDTO(final String firstName,
                                               final String lastName,
                                               final String username) {

        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
    }

    /**
     * Validates and sets a <b>First name</b> variable.
     *
     * @param firstName the Quality Assurance Engineer's <b>First name</b>
     * @throws IllegalArgumentException if any of the following validation conditions fail:
     *                                  <ul>
     *                                  <li>if First name is null</li>
     *                                  <li>if First name length is less than {@linkplain #MINIMUM_NAME_LENGTH}</li>
     *                                  <li>if First name length is greater than {@linkplain #MAXIMUM_NAME_LENGTH}</li>
     *                                  </ul>
     */
    public void setFirstName(final String firstName) {
        if(firstName == null)
        {
            throw new IllegalArgumentException("First name cannot be null");
        }
        if(firstName.length() < MINIMUM_NAME_LENGTH || firstName.length() > MAXIMUM_NAME_LENGTH) {
          throw new IllegalArgumentException(String.format("First name cannot be less than %d or greater than %d", MINIMUM_NAME_LENGTH, MAXIMUM_NAME_LENGTH));
        }
        this.firstName = firstName;
    }

    /**
     * Validates and sets a <b>Last name</b> variable
     *
     * @param lastName the Quality Assurance Engineer's <b>Last name</b>
     * @throws IllegalArgumentException if any of the following validation conditions fail:
     *                                  <ul>
     *                                  <li>if Last name is null</li>
     *                                  <li>if Last name length is less than {@linkplain #MAXIMUM_NAME_LENGTH}</li>
     *                                  <li>if Last name length is less than {@linkplain #MAXIMUM_NAME_LENGTH}</li>
     *                                  </ul>
     */
    public void setLastName(final String lastName) {
        if(lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null");
        }
        if(lastName.length() < MINIMUM_NAME_LENGTH || lastName.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("First name cannot be less than %d or greater than %d", MINIMUM_NAME_LENGTH, MAXIMUM_NAME_LENGTH));
        }
        this.lastName = lastName;
    }

    /**
     * Validates and sets a <b>Username</b> variable
     *
     * @param username the Quality Assurance Engineer's system set username
     * @throws IllegalArgumentException if any of the following validations conditions fail:
     *                                  <ul>
     *                                  <li>if Username is null</li>
     *                                  <li>if Username length is less than {@linkplain #MINIMUM_USERNAME_LENGTH}</li>
     *                                  <li>if Username length is greater than {@linkplain #MAXIMUM_USERNAME_LENGTH}</li>
     *                                  </ul>
     */
    public void setUsername(final String username) {
        if (username == null){
            throw new IllegalArgumentException("Username cannot be null");
        }
        if(username.length() < MINIMUM_USERNAME_LENGTH || username.length() > MAXIMUM_USERNAME_LENGTH){
            throw new IllegalArgumentException(String.format("Username cannot be less than %d or greater than %d", MINIMUM_USERNAME_LENGTH, MAXIMUM_USERNAME_LENGTH));
        }
        this.username = username;
    }
}
