package de.revolut.api;

import java.util.List;

public class AccountCollectionDTO {

    List<AccountResponseDTO> accountResponseDTOS;

    public AccountCollectionDTO() {
    }

    public AccountCollectionDTO(List<AccountResponseDTO> accountResponseDTOS) {
        this.accountResponseDTOS = accountResponseDTOS;
    }

    public List<AccountResponseDTO> getAccounts() {
        return accountResponseDTOS;
    }
}
