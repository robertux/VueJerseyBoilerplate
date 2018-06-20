package com.robertux.test.restApis.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.robertux.test.restApis.data.TestData;

@Path("/test")
public class TestController {
	public static final String UPLOAD_PATH = "/Users/robertux/";
	
	@Context
	 HttpServletRequest request;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData() {
		List<TestData> dataList;
		if (request.getSession(true).getAttribute("dataList") != null && request.getSession(true).getAttribute("dataList") instanceof ArrayList) {
			dataList = (ArrayList<TestData>)request.getSession(true).getAttribute("dataList");
		} else {
			dataList = new ArrayList<TestData>();
			dataList.add(new TestData(1, "name1", new Date(), 1.11));
			dataList.add(new TestData(2, "name2", new Date(), 2.22));
			dataList.add(new TestData(3, "name3", new Date(), 3.33));
			dataList.add(new TestData(4, "name4", new Date(), 4.44));
			dataList.add(new TestData(5, "name5", new Date(), 5.55));
			request.getSession(true).setAttribute("dataList", dataList);
		}
		System.out.println("Devolviendo lista " + Arrays.toString(dataList.toArray()));
		return Response.status(Status.OK).entity(dataList).build();
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addData(TestData newData) {
		List<TestData> dataList;
		if (request.getSession(true).getAttribute("dataList") != null && request.getSession(true).getAttribute("dataList") instanceof ArrayList) {
			dataList = (ArrayList<TestData>)request.getSession(true).getAttribute("dataList");
		} else {
			dataList = new ArrayList<TestData>();
		}
		System.out.println("Agregando TestData: " + newData);
		dataList.add(newData);
		System.out.println("Nueva lista: " + Arrays.toString(dataList.toArray()));
		return Response.status(Status.OK).entity("Allright!").build();
	}
	
	@POST
	@Path("/upload")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file") FormDataContentDisposition fileMetaData) {
		System.out.println("fileName: " + fileMetaData.getFileName());
		System.out.println("fileType: " + fileMetaData.getType());
		System.out.println("fileSize: " + fileMetaData.getSize());
		
		File uploadFile = new File(UPLOAD_PATH + fileMetaData.getFileName());
		if (uploadFile.exists()) {
			System.out.println("File exists! proceeding to delete...");
			System.out.println("Deleted? " + uploadFile.delete());
		}
		
		int read = 0;
		byte[] bytes = new byte[1024];
		try {
			OutputStream out = new FileOutputStream(uploadFile);
			while((read = fileInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("Excepción: " + e.getMessage());
			e.printStackTrace();
		}
		
		return Response.ok().build();
	}
}
