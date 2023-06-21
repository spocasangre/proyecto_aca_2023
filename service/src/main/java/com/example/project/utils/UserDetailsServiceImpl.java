package com.example.project.utils;

import com.example.project.models.entities.Usuario;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Usuario user = userRepository.findByCorreo(username)
			        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + username));
		 return UserDetailsImpl.build(user);
	}

}
