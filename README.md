Dependencias:
Spring Web
Spring Data MongoDB
Thymeleaf
Spring Boot DevTools
Lombok

O que muda no código?
1. Entidade muda (não é mais JPA)
Antes (JPA / SQL)
@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private Long id;
}
Agora (MongoDB)
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id; // geralmente String no MongoDB

    private String nome;
    private String email;
}

 Mudança importante
SQL (JPA)	MongoDB
Long id	String id (ObjectId)
@Entity	@Document
@GeneratedValue	não usa

3. Repository muda pouco
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}

4. Service quase não muda
public void salvarUsuario(Usuario usuario){
    usuarioRepository.save(usuario);
}
 Continua igual
 save() ainda faz insert/update automático

public void excluirUsuario(String id){
    usuarioRepository.deleteById(id);
}

5. Controller muda só o tipo do ID
@GetMapping("/editar/{id}")
public String editarUsuario(@PathVariable String id, Model model){
    Usuario usuario = usuarioService.buscarPorId(id);
    model.addAttribute("usuario", usuario);
    return "formulario";
}
@PostMapping("/salvar")
public String salvarUsuario(@ModelAttribute Usuario usuario){
    usuarioService.salvarUsuario(usuario);
    return "redirect:/usuarios";
}

6. Service (buscar)
public Usuario buscarPorId(String id){
    return usuarioRepository.findById(id).orElseThrow();
}
💡 O que NÃO muda (importante)

Mesmo com MongoDB:

✔️ @ModelAttribute continua igual
✔️ fluxo MVC continua igual
✔️ formulário HTML continua igual
✔️ redirect continua igual

⚡ O que muda de verdade

👉 Só isso:

Banco (SQL → MongoDB)
@Entity → @Document
Long → String
Repository JPA → MongoRepository

Configuração no Aplication properties:
1. Configuração no application.properties
🔹 Forma mais comum (URI única)
spring.data.mongodb.uri=mongodb://localhost:27017/usuario_db
🔹 Forma separada (alternativa)
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=usuario_db
⚙️ 2. Precisa criar “Connection class”?

👉 Normalmente NÃO.

O Spring Boot já cria automaticamente a conexão usando o starter:

spring-boot-starter-data-mongodb
🧩 3. Exemplo completo do fluxo
📦 Entidade
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    private String nome;
    private String email;
}
🗂️ Repository
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
⚙️ Service
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public void salvar(Usuario usuario){
        repository.save(usuario);
    }
}
🚀 4. O que acontece “por baixo dos panos”

Quando você inicia o projeto:

👉 Spring Boot lê o application.properties
👉 Cria automaticamente o client do MongoDB
👉 Conecta no banco usuario_db
👉 Disponibiliza o MongoRepository

🧠 Resumo simples

✔️ Você só precisa disso:

spring.data.mongodb.uri=mongodb://localhost:27017/usuario_db

✔️ O resto o Spring faz sozinho

⚠️ Só atenção nisso
MongoDB precisa estar rodando (local ou Docker)
Porta padrão: 27017
