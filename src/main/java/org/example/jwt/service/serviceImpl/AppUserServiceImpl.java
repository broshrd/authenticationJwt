package org.example.jwt.service.serviceImpl;

import org.example.jwt.model.AppUser;
import org.example.jwt.model.dto.request.AppUserRequest;
import org.example.jwt.model.dto.response.AppUserResponse;
import org.example.jwt.repository.AppUserRepository;
import org.example.jwt.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    public AppUserServiceImpl(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public AppUserResponse register(AppUserRequest appUserRequest) {
        appUserRequest.setPassword(bCryptPasswordEncoder.encode(appUserRequest.getPassword()));
        AppUser appUser = appUserRepository.register(appUserRequest);
        for (String role : appUserRequest.getRoles()) {
            if (role.equals("ROLE_USER")) {
                appUserRepository.insertUserIdAndRoleId(appUser.getId(),1);
            }
            if(role.equals("ROLE_ADMIN")) {
                appUserRepository.insertUserIdAndRoleId(appUser.getId(),2);
            }
        }
        return modelMapper.map(appUserRepository.findByEmail(appUser.getEmail()), AppUserResponse.class);
    }
}
