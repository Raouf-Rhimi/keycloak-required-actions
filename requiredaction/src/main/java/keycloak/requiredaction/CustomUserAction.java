package keycloak.requiredaction;

import org.jboss.logging.Logger;
import org.keycloak.authentication.InitiatedActionSupport;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.validation.Validation;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.function.Consumer;

public class CustomUserAction implements RequiredActionProvider {

	public static final String PROVIDER_ID = "Legitimization";
	private static final String FIRST_IDENTIFIER = "firstIdentifier";
	private static final String SECOND_IDENTIFIER = "secondIdentifier";


	@Override
	public InitiatedActionSupport initiatedActionSupport() {
		return InitiatedActionSupport.SUPPORTED;
	}

	@Override
	public void evaluateTriggers(RequiredActionContext context) {
	}

	@Override
	public void requiredActionChallenge(RequiredActionContext context) {
		context.challenge(createForm(context, null));
	}

	@Override
	public void processAction(RequiredActionContext context) {
		RealmModel realm = context.getRealm();

		try{
			realm.getRole("legitimated").toString(); // you should replace the role name you want to grant to the user.
		}catch (NullPointerException e){
			realm.addRole("legitimated");
		}

		MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
		String firstIdentifier = formData.getFirst(SECOND_IDENTIFIER);
		String secondIdentifier=formData.getFirst(FIRST_IDENTIFIER);

		if (Validation.isBlank(firstIdentifier)) {
			context.challenge(createForm(context, form -> form.addError(new FormMessage(SECOND_IDENTIFIER, "Required"))));
			return;
		}else if (Validation.isBlank(secondIdentifier)){
			context.challenge(createForm(context, form -> form.addError(new FormMessage(FIRST_IDENTIFIER, "Required"))));
			return;
		}else if(!validateInfo(firstIdentifier,secondIdentifier)){
			context.challenge(createForm(context, form -> form.addError(new FormMessage(SECOND_IDENTIFIER, "Wrong data"))));
			return;
		}
		UserModel user = context.getUser();
		user.grantRole(realm.getRole("legitimated"));
		context.success();
	}

	@Override
	public void close() {

	}

	private Response createForm(RequiredActionContext context, Consumer<LoginFormsProvider> formConsumer) {
		LoginFormsProvider form = context.form();
		form.setAttribute(SECOND_IDENTIFIER,  "");
		form.setAttribute(FIRST_IDENTIFIER,"");

		if (formConsumer != null) {
			formConsumer.accept(form);
		}
		return form.createForm("input_info.ftl");
	}

	public Boolean validateInfo(String firstIdentifier,String secondIdentifier){
		// in this method, we should implement our validation logic.that means the call of the API is going to be here
		return firstIdentifier.equals("01/01/2000") && secondIdentifier.equals("CODE");
	}


}
