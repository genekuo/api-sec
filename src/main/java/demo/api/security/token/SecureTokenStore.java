package demo.api.security.token;

public interface SecureTokenStore extends ConfidentialTokenStore,
        AuthenticatedTokenStore {
}
