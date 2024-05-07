package com.nm.authrbac.entity;

public class RequestBodies {

    public static class LoginAuthRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class ResetPasswordRequest {
        private String oldPassword;
        private String newPassword;

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    public static class PostSecretRequest {
        private String secret;
        private String[] authorized_roles;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String[] getAuthorized_roles() {
            return authorized_roles;
        }

        public void setAuthorized_roles(String[] authorized_roles) {
            this.authorized_roles = authorized_roles;
        }
    }

}
