package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios";
    }

        @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model){
        model.addAttribute("usuario", new Usuario());
        return "formulario";
        
    }
    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario){
        usuarioService.salvarUsuario(usuario);
        return "redirect:/usuarios";
    }
}
