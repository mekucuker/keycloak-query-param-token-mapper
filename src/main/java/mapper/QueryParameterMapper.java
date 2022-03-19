package mapper;

import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

/**
 * This implementation provides you to generate custom token mapper
 * based on the query parameters given during authorization request.
 * It can be configured in any custom client scope.
 *
 * @author Mehmet Emin Küçüker, @mekucuker
 */
public class QueryParameterMapper extends AbstractOIDCProtocolMapper {

    @Override
    public String getDisplayCategory() {
        return null;
    }

    @Override
    public String getDisplayType() {
        return null;
    }

    @Override
    public String getHelpText() {
        return null;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
