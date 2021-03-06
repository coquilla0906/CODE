	package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import model.Employee;
import model.Project;
import model.ProjectAssignment;
import model.builder.ProjectFilterQueryBuilder;
import model.builder.QueryFilterDirector;

public class ProjectDAO implements IDBCUD {

	@Override
	public Iterator get() {
		Connection con = DBConnection.getConnection();
        ArrayList<Project> projects = new ArrayList<Project>();
        Date startDate, endDate;
        try {
            String query = "SELECT * FROM project";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                startDate = new java.util.Date(resultSet.getDate("startDate").getTime());
                endDate = new java.util.Date(resultSet.getDate("endDate").getTime());
                Project project = new Project(resultSet.getString("name"), startDate, endDate);
                projects.add(project);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }

        return projects.iterator();
	}
	
	public Iterator getEmployees(String key) {
		
        
		Connection con = DBConnection.getConnection();
        ArrayList<ProjectAssignment> projectAssignment = new ArrayList<ProjectAssignment>();
        
        try {
            String query = "SELECT * FROM projectassignment where project = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	ProjectAssignment pa = new ProjectAssignment(resultSet.getString("project"), resultSet.getInt("employeeID"));
            	projectAssignment.add(pa);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }

        return projectAssignment.iterator();
	}

	@Override
	public Object get(String key) {
		Connection con = DBConnection.getConnection();
        try {

            String query = "SELECT * FROM project where name =  ? ORDER  BY 1";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            Date startDate, endDate;
            if (resultSet.next()) {
            	startDate = new java.util.Date(resultSet.getDate("startDate").getTime());
                endDate = new java.util.Date(resultSet.getDate("endDate").getTime());
            	Project project = new Project(resultSet.getString("name"), startDate, endDate);
                

                try {
                    if(con!=null)
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                return project;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }

        return null;
	}

	@Override
	public Iterator search(String searchStr) {
		// TODO Auto-generated method stub
		Connection con = DBConnection.getConnection();
        String strings[] = searchStr.split(" "); //assuming string format is number space period of time e.g. "10 days"
        ArrayList<Project> projects = new ArrayList<Project>();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        java.sql.Date date = new java.sql.Date(gregorianCalendar.getTimeInMillis());
        String dateNow = date.toString();
        Date startDate, endDate;
        if (strings[1].equalsIgnoreCase("days")) {
            int day = gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + (Integer.parseInt(strings[0]));
            gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, day);
            date = new java.sql.Date(gregorianCalendar.getTimeInMillis());
            searchStr = date.toString();
        }

        try {
            String query = "SELECT * FROM project WHERE endDate <= ? AND endDate >=  ?" + "ORDER BY 1";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "\'" + searchStr + "\'");
            preparedStatement.setString(2, "\'" + dateNow + "\'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                startDate = new java.util.Date(resultSet.getDate("startDate").getTime());
                endDate = new java.util.Date(resultSet.getDate("endDate").getTime());
                Project project = new Project(resultSet.getString("name"), startDate, endDate);
                projects.add(project);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return projects.iterator();
	}

	@Override
	public void add(Object object) {
		Connection con = DBConnection.getConnection();
        Project project = (Project) object;
        try {

            String query = "INSERT INTO project VALUES(?,?,?);";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setDate(2, new java.sql.Date(project.getStartDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(project.getEndDate().getTime()));
            
         
            preparedStatement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
		
	}

	@Override
	public void update(Object object, String origKey) {
		Connection con = DBConnection.getConnection();
		Project project = (Project) object;
        try {
            String query = "UPDATE project SET name = ?,startDate = ?, "
                    + "endDate= ? where name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setDate(2, new java.sql.Date(project.getStartDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(project.getEndDate().getTime()));
            preparedStatement.setString(4, origKey);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
	}
	
	public Iterator filter(Iterator conditions) {
        Connection con = DBConnection.getConnection();
        ArrayList<Project> projects = new ArrayList<Project>();
        QueryFilterDirector director = new QueryFilterDirector(new ProjectFilterQueryBuilder());
        try {
            String query = director.getQuery(conditions);
            //System.out.println(query + " method\n");
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project(resultSet.getString("name"), resultSet.getDate("startDate"), resultSet.getDate("endDate"));
                projects.add(project);
            }
        } catch (Exception Exception) {
            Exception.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return projects.iterator();
    }

	@Override
	public void delete(Object object) {
		Connection con = DBConnection.getConnection();
        Project project = (Project) object;
        try {
            String query = "DELETE FROM project WHERE name = ?;";
            PreparedStatement preparedStatement = con
                    .prepareStatement(query);
            preparedStatement.setString(1, project.getName());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
    }
	
	public void deleteAssignment(Object object) {
		Connection con = DBConnection.getConnection();
        Project project = (Project) object;
        try {
            String query = "DELETE FROM projectassignment WHERE project = ?;";
            PreparedStatement preparedStatement = con
                    .prepareStatement(query);
            preparedStatement.setString(1, project.getName());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
    }
	
	public void addEmployees(Object object, Object object2){
    	Employee employee = (Employee) object;
    	Project project = (Project) object2;
    	
        String query = "INSERT IGNORE INTO projectassignment VALUES(?,?);";
           
            try {
            	PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query);
				preparedStatement.setString(1, project.getName());
				preparedStatement.setInt(2, employee.getID());
	            preparedStatement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    }
	
	
	
	

}
