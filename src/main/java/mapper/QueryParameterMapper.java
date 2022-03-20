package mapper;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;

import java.util.ArrayList;
import java.util.List;

import static org.keycloak.protocol.oidc.endpoints.AuthorizationEndpoint.LOGIN_SESSION_NOTE_ADDITIONAL_REQ_PARAMS_PREFIX;

/**
 * This implementation provides you to generate custom mapper for access token and id token
 * based on the query parameters given during authorization request.
 * It can be configured in any custom client scope.
 *
 * @author Mehmet Emin Küçüker, @mekucuker
 */
public class QueryParameterMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper, OIDCIDTokenMapper {

    public static final String PROVIDER_ID = "oauth2-query-param-mapper";
    public static final String QUERY_PARAM = "query.param";

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    static {
        ProviderConfigProperty queryParam = new ProviderConfigProperty();
        queryParam.setName(QUERY_PARAM);
        queryParam.setLabel("Query Parameter Key");
        queryParam.setHelpText("The query parameter which will be given during the authorization request and mapped to the token.");
        queryParam.setType(ProviderConfigProperty.STRING_TYPE);
        queryParam.setDefaultValue("param");
        configProperties.add(queryParam);

        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, QueryParameterMapper.class);
    }

    @Override
    public String getDisplayCategory() {
        return TOKEN_MAPPER_CATEGORY;
    }

    @Override
    public String getDisplayType() {
        return "Query Parameter";
    }

    @Override
    public String getHelpText() {
        return "Maps the query parameter given during the authorization request to the token claim with the parameter name.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    /**
     * Sets the claim in the selected tokens by taking value from the query parameter given during the authorization request.
     * The value is taken from the notes in the context of the client session.
     *
     * @param token
     * @param mappingModel
     * @param userSession
     * @param keycloakSession
     * @param clientSessionCtx
     */
    @Override
    protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession, KeycloakSession keycloakSession, ClientSessionContext clientSessionCtx) {
        String claimValue = mappingModel.getConfig().get(QUERY_PARAM);
        String clientSessionNote = clientSessionCtx.getClientSession().getNote(LOGIN_SESSION_NOTE_ADDITIONAL_REQ_PARAMS_PREFIX + claimValue);

        OIDCAttributeMapperHelper.mapClaim(token, mappingModel, clientSessionNote);
    }
}
