package br.com.fiap.resource;


import br.com.fiap.bo.MensagemBO;
import br.com.fiap.dto.mensagem.AtualizarMensagemDto;
import br.com.fiap.dto.mensagem.CadastroMensagemDto;
import br.com.fiap.dto.mensagem.DetalhesMensagemDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Mensagem;
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

@Path("/mensagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensagemResource {

    @Inject
    private MensagemBO mensagemBO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Mensagem> listar() throws SQLException {
        return mensagemBO.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws SQLException, EntidadeNaoEncontrada {
        Mensagem mensagem = mensagemBO.buscar(codigo);
        return Response.ok(mapper.map(mensagem, DetalhesMensagemDto.class)).build();
    }

    @GET
    @Path("/usuarios/{codigoUsuario}")
    public Response buscarPorUsuario(@PathParam("codigoUsuario") int codigoUsuario) throws SQLException {
        List<Mensagem> mensagens = mensagemBO.buscarPorUsuario(codigoUsuario);
        return Response.ok(mensagens).build();
    }

    @GET
    @Path("/projetos/{codigoProjeto}")
    public Response buscarPorProjeto(@PathParam("codigoProjeto") int codigoProjeto) throws SQLException {
        List<Mensagem> mensagens = mensagemBO.buscarPorProjeto(codigoProjeto);
        return Response.ok(mensagens).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws SQLException, EntidadeNaoEncontrada {
        mensagemBO.remover(codigo);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int codigo, @Valid AtualizarMensagemDto dto) throws SQLException, EntidadeNaoEncontrada {
        Mensagem mensagem = mapper.map(dto, Mensagem.class);
        mensagem.setCodigo(codigo);
        mensagemBO.atualizar(mensagem);
        return Response.ok().build();
    }

    @POST
    public Response criar(@Valid CadastroMensagemDto dto, @Context UriInfo uriInfo) throws SQLException {
        Mensagem mensagem = mapper.map(dto, Mensagem.class);
        mensagemBO.cadastrar(mensagem);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(mensagem.getCodigo())).build();
        return Response.created(uri).entity(mapper.map(mensagem, DetalhesMensagemDto.class)).build();
    }
}
