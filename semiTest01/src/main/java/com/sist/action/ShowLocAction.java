package com.sist.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.CustomerDAO;
import com.sist.dao.LocationDAO;
import com.sist.vo.LocationVO;
public class ShowLocAction implements SistAction {

	public LocationDAO dao;
	public ShowLocAction() {
		dao = new LocationDAO();
	}
	
	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ArrayList<LocationVO> list = dao.listAll();
		HashMap<String,ArrayList<String>> districts = new HashMap<String,ArrayList<String>>();
		HashMap<String,ArrayList<String>> dongs = new HashMap<String,ArrayList<String>>();
		
		ArrayList<String> provinces = dao.getProvince();
		
		for(LocationVO l : list) {
			if(districts.get(l.getProvince())==null) {
				districts.put(l.getProvince(), dao.getDistrict(l.getProvince()));
			}
		}
		for(LocationVO l : list) {
			if(dongs.get(l.getDistrict())==null) {
				dongs.put(l.getDistrict(), dao.getDong(l.getDistrict()));
			}
		}
		
		request.setAttribute("provinces", provinces);
		request.setAttribute("districts", districts);
		request.setAttribute("dongs", dongs);
		
		
		return "showLoc.jsp";
	}
	
}
