package net.tfobz.notizblock.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response; 

@Path("notizblock")
public class Notizblock {
	
	protected NotizblockIntern nintern = new NotizblockIntern();
	
//	public ArrayList<Thema> getThemenOhneWebService() {
//		if (themen.size() == 0)
//			return null;
//		else
//			return themen;
//	}
	
	@GET
	@Path("themen/{benutzername}/{passwort}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getThemen(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort) {
		ArrayList<Thema> themen = nintern.getThemen(benutzername, passwort);
		if (themen.size() == 0)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Liste ist leer")
					.build();
		else {
			GenericEntity<List<Thema>> list = new GenericEntity<List<Thema>>(themen) {};
			return Response
					.status(Response.Status.OK)
					.entity(list)
					.build();
		}
	}

	@GET
	@Path("thema/{benutzername}/{passwort}/{nummer}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getThema(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort, @PathParam("nummer") Integer nummer) {
		Thema thema = nintern.getThema(benutzername, passwort, nummer);
		if (thema == null)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Das Thema wurde nicht gefunden")
					.build();
		else {
			GenericEntity<Thema> resp = new GenericEntity<Thema>(thema) {};
			return Response
					.status(Response.Status.OK)
					.entity(resp)
					.build();
		}
	}
	
	@GET
	@Path("benutzer/{benutzername}/{passwort}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBenutzer(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort) {
		Benutzer user = nintern.getBenutzer(benutzername, passwort);
		if (user == null)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Der Benutzer wurde nicht gefunden")
					.build();
		else {
			GenericEntity<Benutzer> resp = new GenericEntity<Benutzer>(user) {};
			return Response
					.status(Response.Status.OK)
					.entity(resp)
					.build();
		}
	}
	
	@GET
	@Path("notizen/{benutzername}/{passwort}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllNotizen(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort) {
		ArrayList<Notiz> notizen = nintern.getNotizen(benutzername, passwort);
		if (notizen == null || notizen.size() == 0)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Keine Notizen gefunden")
					.build();
		else {
			GenericEntity<List<Notiz>> list = new GenericEntity<List<Notiz>>(notizen) {};
			return Response
					.status(Response.Status.OK)
					.entity(list)
					.build();
		}
	}
	
	@GET
	@Path("notizen/{benutzername}/{passwort}/{sortierung}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllNotizenSorted(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort, @PathParam("sortierung") String sortierung) {
		ArrayList<Notiz> notizen = nintern.getNotizen(benutzername, passwort, sortierung);
		if (notizen == null || notizen.size() == 0)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Keine Notizen gefunden")
					.build();
		else {
			GenericEntity<List<Notiz>> list = new GenericEntity<List<Notiz>>(notizen) {};
			return Response
					.status(Response.Status.OK)
					.entity(list)
					.build();
		}
	}
	
	@GET
	@Path("notiz/{benutzername}/{passwort}/{nummer}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getNotiz(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort, @PathParam("nummer") Integer nummer) {
		Notiz notiz = nintern.getNotiz(benutzername, passwort, nummer);
		if (notiz == null)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Die notiz wurde nicht gefunden")
					.build();
		else {
			GenericEntity<Notiz> resp = new GenericEntity<Notiz>(notiz) {};
			return Response
					.status(Response.Status.OK)
					.entity(resp)
					.build();
		}
	}
	
	@POST
	@Path("notiz/{benutzername}/{passwort}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response hinzufuegenNotiz(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort, Notiz notiz) {
		Notiz note = nintern.hinzufuegenNotiz(benutzername, passwort, notiz);
		GenericEntity<Notiz> resp = new GenericEntity<Notiz>(note) {};
		if (note.fehler != null)
			return Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity(resp)
					.build();
		else {
			return Response
					.status(Response.Status.CREATED)
					.entity(resp)
					.build();
		}
	}
	
	@PUT
	@Path("notiz/{benutzername}/{passwort}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public Response aendernNotiz(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort, Notiz notiz) {
		Notiz note = nintern.aendernNotiz(benutzername, passwort, notiz);
		GenericEntity<Notiz> resp = new GenericEntity<Notiz>(note) {};
		if (note.fehler != null)
			return Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity(resp)
					.build();
		else {
			return Response
					.status(Response.Status.CREATED)
					.entity(resp)
					.build();
		}
	}
	
	@PUT
	@Path("notiz/{benutzername}/{passwort}/{nummer}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gelesenNotiz(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort, @PathParam("nummer") Integer nummer) {
		Notiz note = nintern.setGelesenNotiz(benutzername, passwort, nummer);
		if (note == null)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Die Notiz konnte nicht gefunden werden")
					.build();
		else {
			return Response
					.status(Response.Status.OK)
					.entity("Der Status wurde geändert")
					.build();
		}
	}
	
	@DELETE
	@Path("notiz/{benutzername}/{passwort}/{nummer}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response deleteNotiz(@PathParam("benutzername") String benutzername,
			@PathParam("passwort") String passwort, @PathParam("nummer")Integer nummer) {
		Notiz note = nintern.loeschenNotiz(benutzername, passwort, nummer);
		if (note == null)
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Die zu löschende Notiz konnte nicht gefunden werden")
					.build();
		else {
			return Response
					.status(Response.Status.OK)
					.entity("Die Notiz wurde erfolgreich gelöscht")
					.build();
		}
	}
}
