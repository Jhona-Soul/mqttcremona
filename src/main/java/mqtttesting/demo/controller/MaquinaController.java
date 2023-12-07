package mqtttesting.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/etiquetas")
public class MaquinaController {


    @GetMapping("/guardar")
    public ResponseEntity<?> guardarEtiqueta() {

        System.out.println("Mensaje de prueba");

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
