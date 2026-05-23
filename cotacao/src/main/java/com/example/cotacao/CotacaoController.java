package com.example.cotacao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CotacaoController {
    private final CotacaoRepository repository;
    public CotacaoController(CotacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/calcular")
    public String calcular(
        @RequestParam String produto,
        @RequestParam double preco,
        @RequestParam int quantidade,
        Model model
    ) {
        double total = preco * quantidade;
        double imposto = preco * 0.10; // Exemplo de imposto de 10%
        double ValorTotal = total + imposto;
       
        String status;
        if (ValorTotal > 100) {
            status = "Caro";
        } else {
            status = "Barato";
        }

        Cotacao c = new Cotacao();
        c.setProduto(produto);
        c.setPreco(preco);
        c.setQuantidade(quantidade);
        c.setTotal(total);
        c.setImposto(imposto);
        c.setValorTotal(ValorTotal);
        c.setStatus(status);
        
        repository.save(c);
        
        model.addAttribute("produto", produto);
        model.addAttribute("preco", preco);
        model.addAttribute("total", total);
        model.addAttribute("imposto", imposto);
        model.addAttribute("ValorTotal", ValorTotal);
        model.addAttribute("status", status);
        
        return "resultado";
        
        
        
    }
    @GetMapping("/historico")
    public String historico(Model model) {
        model.addAttribute("lista", repository.findAll());
        return "historico";
    }
}
