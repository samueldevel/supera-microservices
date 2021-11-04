package samueldev.projects.auth.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import samueldev.projects.auth.requests.RequestApplicationUserToCreateToken;
import samueldev.projects.core.domain.ApplicationUser;

@Mapper(componentModel = "spring")
public abstract class ApplicationUserMapper {
    public static final ApplicationUserMapper INSTANCE = Mappers.getMapper(ApplicationUserMapper.class);

    public abstract ApplicationUser toApplicationUser(RequestApplicationUserToCreateToken applicationUserToCreateToken);
}
