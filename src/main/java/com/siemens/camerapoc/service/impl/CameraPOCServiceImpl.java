package com.siemens.camerapoc.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.siemens.camerapoc.beans.CameraImageBean;
import com.siemens.camerapoc.beans.UserDetailsBean;

@Service
public class CameraPOCServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(CameraPOCServiceImpl.class);
	private final String dbUrl = "jdbc:postgresql://localhost:5432/app_db";
	private final String dbUser = "postgres";
	private final String dbPassword = "postgres";

	public List<CameraImageBean> getAllImagesFromDB(int count) throws SQLException {
		List<CameraImageBean> iiim = new ArrayList<CameraImageBean>();

		Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from employee_info order by row_id desc limit " + count);
			while (rs.next()) {
				CameraImageBean image = new CameraImageBean();
				image.setCount(rs.getInt("inward_count"));
				image.setTimeStamp(rs.getString("time_stamp"));
				List<UserDetailsBean> udb = new ArrayList<UserDetailsBean>();
				//CameraImageBean ub = new CameraImageBean();
				image.setUsers(new Gson().fromJson(rs.getString("user_json"), udb.getClass()));
				iiim.add(image);
				logger.info("the value is " + rs.getInt("inward_count") + " " + rs.getString("time_stamp")+" "+rs.getString("user_json"));
			}
			logger.info("the final data is "+new Gson().toJson(iiim));
			conn.close();
		} catch (SQLException e) {
			logger.error("error while getting connection from DB " + e);
		} finally {
			conn.close();
		}

		/*
		 * CameraImageBean i1 = new CameraImageBean(); i1.setCount(3);
		 * i1.setTimeStamp("testcase1"); CameraImageBean i2 = new CameraImageBean();
		 * i2.setCount(3); i2.setTimeStamp("testcase3"); iiim.add(i1); iiim.add(i2);
		 */
		return iiim;
	}

	public void insertImageIntoDB(List<CameraImageBean> images) throws SQLException {
		logger.error("inside service class");
		Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		PreparedStatement ps = conn
				.prepareStatement("insert into employee_info (inward_count,time_stamp,user_json) values (?,?,?)");
		for (CameraImageBean image : images) {
			ps.setInt(1, image.getCount());
			ps.setString(2, image.getTimeStamp());
			ps.setString(3, new Gson().toJson(image.getUsers()));
			ps.execute();
		}
		conn.close();
	}

}
