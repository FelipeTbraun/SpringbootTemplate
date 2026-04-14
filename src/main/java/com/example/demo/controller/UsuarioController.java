package com.example.demo.controller;

import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
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
    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id){
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
public String editarUsuario(@PathVariable Long id, Model model){
    Usuario usuario = usuarioService.buscarPorId(id);
    model.addAttribute("usuario", usuario);
    return "formulario";
}
    

}
