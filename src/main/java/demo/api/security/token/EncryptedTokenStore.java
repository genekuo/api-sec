package demo.api.security.token;

import software.pando.crypto.nacl.SecretBox;
import spark.Request;

import java.security.Key;
import java.util.Optional;

public class EncryptedTokenStore implements SecureTokenStore {

    private final TokenStore delegate;
    private final Key encryptionKey;

    public EncryptedTokenStore(TokenStore delegate, Key encryptionKey) {
        this.delegate = delegate;
        this.encryptionKey = encryptionKey;
    }

    @Override
    public String create(Request request, Token token) {
        var tokenId = Base64url.decode(delegate.create(request, token));
        return SecretBox.encrypt(encryptionKey, tokenId).toString();
    }

    @Override
    public Optional<Token> read(Request request, String tokenId) {
        var box = SecretBox.fromString(tokenId);
        var originalTokenId = Base64url.encode(box.decrypt(encryptionKey));
        return delegate.read(request, originalTokenId);
    }

    @Override
    public void revoke(Request request, String tokenId) {
        var box = SecretBox.fromString(tokenId);
        var originalTokenId = Base64url.encode(box.decrypt(encryptionKey));
        delegate.revoke(request, originalTokenId);
    }
}
