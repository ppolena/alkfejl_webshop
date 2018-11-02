package alkfejl_webshop.entity;

import org.springframework.security.core.GrantedAuthority;

public enum AccessRight implements GrantedAuthority{
    ROLE_ADMIN, ROLE_CUSTOMER, ROLE_GUEST;

    @Override
    public String getAuthority(){
        return this.toString();
    }
}
