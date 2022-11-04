/* Decompiler 7ms, total 347ms, lines 90 */
package customuserfedprovider;

import java.util.HashMap;
import java.util.Map;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

public class HashMapStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {
   private HashMapUserStore hashMapUserStore;
   KeycloakSession keycloakSession;
   ComponentModel componentModel;
   protected Map<String, UserModel> loadedUsers = new HashMap();

   public HashMapStorageProvider(KeycloakSession session, ComponentModel model, HashMapUserStore hashMapUserStore) {
      this.hashMapUserStore = hashMapUserStore;
      this.keycloakSession = session;
      this.componentModel = model;
   }

   public void close() {
   }

   public UserModel getUserById(String id, RealmModel realm) {
      StorageId storageId = new StorageId(id);
      String username = storageId.getExternalId();
      return this.getUserByUsername(username, realm);
   }

   public UserModel getUserByUsername(String username, RealmModel realm) {
      UserModel adapter = (UserModel)this.loadedUsers.get(username);
      if (adapter == null) {
         User user = this.hashMapUserStore.getUser(username);
         if (user != null) {
            adapter = this.createAdapter(realm, username);
            this.loadedUsers.put(username, adapter);
         }
      }

      return adapter;
   }

   public UserModel getUserByEmail(String email, RealmModel realm) {
      return null;
   }

   public boolean supportsCredentialType(String credentialType) {
      return credentialType.equals("password");
   }

   public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
      try {
         String password = this.hashMapUserStore.getUser(user.getUsername()).getPassword();
         return credentialType.equals("password") && password != null;
      } catch (Exception var5) {
         var5.printStackTrace();
         return false;
      }
   }

   public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
      if (!this.supportsCredentialType(credentialInput.getType())) {
         return false;
      } else {
         try {
            String password = this.hashMapUserStore.getUser(user.getUsername()).getPassword();
            return password == null ? false : password.equals(credentialInput.getChallengeResponse());
         } catch (Exception var5) {
            var5.printStackTrace();
            return false;
         }
      }
   }

   private UserModel createAdapter(RealmModel realm, final String username) {
      return new AbstractUserAdapter(this.keycloakSession, realm, this.componentModel) {
         public String getUsername() {
            return username;
         }
      };
   }
}