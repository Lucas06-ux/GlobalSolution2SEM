package br.com.fiap.resource;


import br.com.fiap.bo.UsuarioBO;
import br.com.fiap.dto.usuario.AtualizarUsuarioDto;
import br.com.fiap.dto.usuario.CadastroUsuarioDto;
import br.com.fiap.dto.usuario.DetalhesUsuarioDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Usuario;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    private UsuarioBO usuarioBO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Usuario> listar() throws SQLException{
        return usuarioBO.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException{
        Usuario usuario = usuarioBO.buscar(codigo);
        return Response.ok(mapper.map(usuario, DetalhesUsuarioDto.class)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        usuarioBO.remover(codigo);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id")int codigo, @Valid AtualizarUsuarioDto dto) throws EntidadeNaoEncontrada, SQLException {
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setCodigo(codigo);
        usuarioBO.atualizar(usuario);
        return Response.ok().build();
    }
    @POST
    public Response criar (@Valid CadastroUsuarioDto dto, @Context UriInfo uriInfo) throws SQLException {
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuarioBO.cadastrar(usuario);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(usuario.getCodigo())).build();
        return Response.created(uri).entity(mapper.map(usuario, DetalhesUsuarioDto.class)).build();
    }
}
