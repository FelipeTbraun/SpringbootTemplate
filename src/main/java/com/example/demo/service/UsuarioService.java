package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos(){
        //SELECT * FROM USUARIO;
        return usuarioRepository.findAll();
    }
    public Usuario salvarUsuario(Usuario usuario){
        //INSERT INTO USUARIO (ID, NOME, EMAIL) VALUES (id, nome, email)
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id){
        //SELECT * FROM USUARIO WHERE ID = id;
        return usuarioRepository.findById(id).orElse(null);
    }
    public void excluirUsuario(Long id){
        //DELETE * FROM USUARIO WHERE ID = id
        usuarioRepository.deleteById(id);
    }
  
}
