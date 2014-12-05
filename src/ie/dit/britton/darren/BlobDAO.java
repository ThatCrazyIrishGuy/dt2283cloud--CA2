package ie.dit.britton.darren;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;

public class BlobDAO {
	
	String url;
	
	public BlobDAO()
	{
		url = null;
	    try {
	      if (SystemProperty.environment.value() ==
	          SystemProperty.Environment.Value.Production) {
	        // Load the class that provides the new "jdbc:google:mysql://" prefix.
	        Class.forName("com.mysql.jdbc.GoogleDriver");
	        url = "jdbc:google:mysql://ieditbrittondarrenpicturebox:picinfo?user=root";
	      } else {
	        // Local MySQL instance to use during development.
	        Class.forName("com.mysql.jdbc.Driver");
	        url = "jdbc:mysql://127.0.0.1:3306/pictureinfo?user=root";
	
	        // Alternatively, connect to a Google Cloud SQL instance using:
	        // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/pictureinfo?user=root
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public void uploadInfo(List<BlobKey> blobKey)
	{
	
	    try {
	      Connection conn = DriverManager.getConnection(url);
	      try {
    		  UserService userService = UserServiceFactory.getUserService(); //gets instance of user service instance
    		  String name = userService.getCurrentUser().getNickname();
    		  Integer isPublic = (userService.isUserAdmin()) ? 1: 0;
    		  
    		  for (BlobKey key : blobKey)
    		  {
    	          String statement = "INSERT INTO pictureinfo.picinfo (blobKey, uploaderName, public) VALUES( ? , ? , ? )";
    	          PreparedStatement stmt = conn.prepareStatement(statement);
    	          stmt.setString(1, key.getKeyString());
    	          stmt.setString(2, name);
    	          stmt.setInt(3, isPublic);
    	          stmt.executeUpdate();
    		  }
	        }
	      finally {
	        conn.close();
	      }
	    } catch (SQLException e) {
		      e.printStackTrace();
		      return;
	    }
	 }
	
	public List<String> getUserBlobs()
	{
		ArrayList<String> blobs = new ArrayList<String>();
		
	    try {
		      Connection conn = DriverManager.getConnection(url);
		      try {
	    		  UserService userService = UserServiceFactory.getUserService(); //gets instance of user service instance
	    		  String name = userService.getCurrentUser().getNickname();

    	          String statement = "SELECT blobKey FROM pictureinfo.picinfo WHERE uploaderName = ? ";
    	          PreparedStatement stmt = conn.prepareStatement(statement);
    	          stmt.setString(1, name);
    	          ResultSet rs = stmt.executeQuery();
    	          
    	          while(rs.next())
    	          {
    	        	  blobs.add(rs.getString("blobKey"));
    	          }
    	          
		        }
		      finally {
		        conn.close();
		      }
		    } catch (SQLException e) {
			      e.printStackTrace();
		    }
		return blobs;
	}
	
	public List<String> getPublicBlobs()
	{
		ArrayList<String> blobs = new ArrayList<String>();
		
	    try {
		      Connection conn = DriverManager.getConnection(url);
		      try {

    	          String statement = "SELECT blobKey FROM pictureinfo.picinfo WHERE public = 1 ";
    	          PreparedStatement stmt = conn.prepareStatement(statement);
    	          ResultSet rs = stmt.executeQuery();
    	          
    	          while(rs.next())
    	          {
    	        	  blobs.add(rs.getString("blobKey"));
    	          }
    	          
		        }
		      finally {
		        conn.close();
		      }
		    } catch (SQLException e) {
			      e.printStackTrace();
		    }
		return blobs;
	}
	
	public List<String> getAllBlobs()
	{
		ArrayList<String> blobs = new ArrayList<String>();
		
	    try {
		      Connection conn = DriverManager.getConnection(url);
		      try {

    	          String statement = "SELECT blobKey FROM pictureinfo.picinfo";
    	          PreparedStatement stmt = conn.prepareStatement(statement);
    	          ResultSet rs = stmt.executeQuery();
    	          
    	          while(rs.next())
    	          {
    	        	  blobs.add(rs.getString("blobKey"));
    	          }
    	          
		        }
		      finally {
		        conn.close();
		      }
		    } catch (SQLException e) {
			      e.printStackTrace();
		    }
		return blobs;
	}
	
	public void deleteBlob(String blobKey)
	{		
	    try {
		      Connection conn = DriverManager.getConnection(url);
		      try {

    	          String statement = "DELETE FROM pictureinfo.picinfo WHERE blobKey = ?";
    	          PreparedStatement stmt = conn.prepareStatement(statement);
    	          stmt.setString(1, blobKey);
    	          stmt.executeUpdate();        
		        }
		      finally {
		        conn.close();
		      }
		    } catch (SQLException e) {
			      e.printStackTrace();
		    }
	}
	
	public void updateBlobVisibility(Integer isPublic, String blobKey)
	{		
	    try {
		      Connection conn = DriverManager.getConnection(url);
		      try {

    	          String statement = "UPDATE pictureinfo.picinfo SET isPublic = ? WHERE blobKey = ?";
    	          PreparedStatement stmt = conn.prepareStatement(statement);
    	          stmt.setInt(1, isPublic);
    	          stmt.setString(2, blobKey);
    	          stmt.executeUpdate();        
		        }
		      finally {
		        conn.close();
		      }
		    } catch (SQLException e) {
			      e.printStackTrace();
		    }
	}
}
