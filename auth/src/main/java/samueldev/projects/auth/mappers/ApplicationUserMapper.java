package samueldev.projects.auth.mappers;

import domain.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.servlet.ServletInputStream;

@Mapper(componentModel = "spring")
public abstract class ApplicationUserMapper {
    public static final ApplicationUserMapper INSTANCE = Mappers.getMapper(ApplicationUserMapper.class);

    public abstract ApplicationUser toApplicationUser(ServletInputStream servletInputStream);
}
