<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('info'); section>
    <#if section = "header">
        ${msg("headerTitle")}
    <#elseif section = "form">
			<p>${msg("expression")}</p>
			<form id="info-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
				<div class="${properties.kcFormGroupClass!}">

					<div class="${properties.kcLabelWrapperClass!}">
						<label for="firstIdentifier"class="${properties.kcLabelClass!}">${msg("firstIdentifier")}</label>
					</div>
					<div class="${properties.kcInputWrapperClass!}">
						<input type="text" id="firstIdentifier" name="firstIdentifier" class="${properties.kcInputClass!}"
									 value="${firstIdentifier}" required aria-invalid="<#if messagesPerField.existsError('firstIdentifier')>true</#if>"/>
              <#if messagesPerField.existsError('firstIdentifier')>
								<span id="input-error-firstIdentifier" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
										${kcSanitize(messagesPerField.get('firstIdentifier'))?no_esc}
								</span>
              </#if>
					</div>
					<div class="${properties.kcLabelWrapperClass!}">
						<label for="secondIdentifier"class="${properties.kcLabelClass!}">${msg("secondIdentifier")}</label>
					</div>
					<div class="${properties.kcInputWrapperClass!}">
						<input type="text" id="secondIdentifier" name="secondIdentifier" class="${properties.kcInputClass!}"
									 value="${secondIdentifier}" required aria-invalid="<#if messagesPerField.existsError('secondIdentifier')>true</#if>"/>
              <#if messagesPerField.existsError('secondIdentifier')>
								<span id="input-error-secondIdentifier" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
										${kcSanitize(messagesPerField.get('secondIdentifier'))?no_esc}
								</span>
              </#if>
					</div>
				</div>

				<div class="${properties.kcFormGroupClass!}">
					<div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
						<input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doSubmit")}"/>
					</div>
				</div>

			</form>
    </#if>
</@layout.registrationLayout>
