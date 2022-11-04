/* Decompiler 3ms, total 1262ms, lines 32 */
package customuserfedprovider;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class HashMapStorageProviderFactory implements UserStorageProviderFactory<HashMapStorageProvider> {
   public static final String PROVIDER_NAME = "hashmap-user-store";
   HashMapUserStore userStore = new HashMapUserStore();

   public HashMapStorageProvider create(KeycloakSession session, ComponentModel model) {
      return new HashMapStorageProvider(session, model, this.userStore);
   }

   public String getId() {
      return "hashmap-user-store";
   }
   
  
   // $FF: synthetic method
   // $FF: bridge method
//   public UserStorageProvider create(KeycloakSession var1, ComponentModel var2) {
//      return this.create(var1, var2);
//   }
//
//   // $FF: synthetic method
//   // $FF: bridge method
//   public Object create(KeycloakSession var1, ComponentModel var2) {
//      return this.create(var1, var2);
//   }
}