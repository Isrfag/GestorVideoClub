package com.empresa.curso.ejercicio17.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empresa.curso.ejercicio17.dto.ClienteDto;
import com.empresa.curso.ejercicio17.entity.Cliente;
import com.empresa.curso.ejercicio17.exception.ClienteNotFoundException;
import com.empresa.curso.ejercicio17.mapper.ClienteMapper;
import com.empresa.curso.ejercicio17.service.ClienteService;

@Controller
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new ClienteDto()); // Usamos DTO
        return "clientes/formulario";
    }

    // Guardar cliente
    @PostMapping("/guardar")
    public String guardarCliente(ClienteDto clienteDto, 
                                 @RequestParam(required = false) Long peliculaId,
                                 RedirectAttributes redirectAttributes) {
        // Convertimos DTO a entidad antes de guardar
        Cliente cliente = ClienteMapper.toEntity(clienteDto);
        clienteService.guardarCliente(cliente);

        redirectAttributes.addFlashAttribute("mensaje", "👤 Cliente creado con éxito.");

        if (peliculaId != null) {
            return "redirect:/alquileres/nuevo/" + peliculaId;
        }

        return "redirect:/alquileres";
    }

    //SOLO ADMINS
    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            model.addAttribute("cliente", ClienteMapper.toDto(cliente));
            return "clientes/formulario";
        } catch (ClienteNotFoundException e) {
            return "redirect:/clientes";
        }
      
    }

    @GetMapping("/admin/nuevo")
    public String mostrarFormularioNuevoAdmin(Model model) {
        model.addAttribute("cliente", new ClienteDto()); // Usamos DTO
        return "clientes/formulario-admin";
    }    

    // Guardar cliente
    @PostMapping("/admin/guardar")
    public String guardarClienteAmin(ClienteDto clienteDto, 
                                 @RequestParam(required = false) Long peliculaId,
                                 RedirectAttributes redirectAttributes) {
        // Convertimos DTO a entidad antes de guardar
        Cliente cliente = ClienteMapper.toEntity(clienteDto);
        clienteService.guardarCliente(cliente);

        redirectAttributes.addFlashAttribute("mensaje", "👤 Cliente creado con éxito.");
        return "redirect:/clientes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clienteService.eliminarCliente(id);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado correctamente.");
        return "redirect:/clientes";
    }    
}
