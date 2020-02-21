import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CommentService {

	public CommentService() {
	}

	public boolean editComment(String raterName, int commentID, String newComment, int newRate) {
		CallableStatement cs = null;

		try {
			cs = Main.connS.getConnection().prepareCall("{call edit_Comment(?,?,?,?)}");
			if (newRate < 0 || newRate > 5) {
				JOptionPane.showMessageDialog(null, "Illegal rate not allow");
				return false;
			}

			if (raterName == null || raterName.isEmpty()) {
				// JOptionPane.showMessageDialog(null, "Empty rater name not allow");
				return false;
			}

			if (newComment == null || newComment.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Empty commnet not allow");
				return false;
			}
			cs.setString(1, raterName);
			cs.setInt(2, commentID);
			cs.setString(3, newComment);
			cs.setInt(4, newRate);
			if (!cs.execute()) {
				JOptionPane.showMessageDialog(null, "Edition succeeded");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Edition failed");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteComment(String username, int commentID) {
		CallableStatement cs = null;

		try {
			cs = Main.connS.getConnection().prepareCall("{call delete_comment(?,?)}");

			if (username == null || username.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Empty username not allow");
				return false;
			}

			cs.setString(1, username);
			cs.setInt(2, commentID);

			if (!cs.execute()) {
				JOptionPane.showMessageDialog(null, "deletion succeeded");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "deletion failed");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

//	@Comment varchar(MAX), 
//	@Rate int, 
//	@RaterName varchar(15),
//	@CourseName varchar(30), 
//	@Professor varchar(15)

	public boolean addComment(String comment, int rate, String raterName, String courseName, String professor) {
		CallableStatement cs = null;

		try {
			cs = Main.connS.getConnection().prepareCall("{? = call insert_Comment(?, ?, ?, ?, ?)}");

			if (comment == null || comment.isEmpty()) {
				// print somethin to JFrame
				JOptionPane.showMessageDialog(null, "Empty comment not allow");
				return false;
			}
			if (rate > 5 || rate < 0) {
				JOptionPane.showMessageDialog(null, "Illegal rate not allow");
				return false;
			}
			if (raterName == null || raterName.isEmpty()) {
				// print somethin to JFrame
				JOptionPane.showMessageDialog(null, "Empty rater Name not allow");
				return false;
			}
			if (courseName == null || courseName.isEmpty()) {
				// print somethin to JFrame
				JOptionPane.showMessageDialog(null, "Empty course name not allow");
				return false;
			}
			if (professor == null || professor.isEmpty()) {
				// print somethin to JFrame
				JOptionPane.showMessageDialog(null, "Empty professor not allow");
				return false;
			}
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, comment);
			cs.setInt(3, rate);
			cs.setString(4, raterName);
			cs.setString(5, courseName);
			cs.setString(6, professor);

			cs.execute();
			int result = cs.getInt(1);

			System.out.println(result);

			if (result == 0) {
				JOptionPane.showMessageDialog(null, "Add succeeded");
				return true;
			} else if (result == 10) {
				JOptionPane.showMessageDialog(null, "The rate is invalid.");
			} else if (result == 20) {
				JOptionPane.showMessageDialog(null, "Sorry, only Students can post comments");
			} else if (result == 30) {
				JOptionPane.showMessageDialog(null, "Course is not valid");
			} else if (result == 40) {
				JOptionPane.showMessageDialog(null, "Please choose a professor");
			} else if (result == 50) {
				JOptionPane.showMessageDialog(null, "Sorry, you didn't take this course and cannot comment it");
			} else if (result == 60) {
				JOptionPane.showMessageDialog(null, "Sorry, you can't rate the same course provided by the same professor twice");
			}

			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

//			[Date]	
//		      ,[content]
//		      ,[rate]
//		      ,[CourseName]
//		      ,[ProfessorName]

	public ArrayList<ArrayList<String>> getComment(String courseName, String profName) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		PreparedStatement ps = null;
//		String statement = "Select c.content, c.rate, c.Date, c2.Name as [Course Name], u.Name as [Professor Name], c.ID, c.raterName "
//				+ "from Comment c join [User] u on c.ProfessorUsername = u.Username join Course c2 on c.CourseID = c2.ID ";
//		String statement = "Select * from fn_showallcomments()";
		String statement = "";
		try {
			if (courseName != null && !courseName.isEmpty()) {
				if (profName != null && !profName.isEmpty()) {
//					statement += "where c2.Name = ? and u.Name = ?";
					statement = "select * from fn_showallcomments1('" + courseName + "', '" + profName + "')";
//					ps = Main.connS.getConnection().prepareStatement(statement);
//					ps.setString(1, courseName);
//					ps.setString(2, profName);
				} else {
					statement = "select * from fn_showallcomments2('" + courseName + "')";
//					ps = Main.connS.getConnection().prepareStatement(statement);
//					ps.setString(1, courseName);
				}
			} else {
				if (profName != null && !profName.isEmpty()) {
					statement = "select * from fn_showallcomments3('" + profName + "')";
//					ps = Main.connS.getConnection().prepareStatement(statement);
//					ps.setString(1, profName);
				} else {
					statement = "select * from fn_showallcomments4('" + courseName + "')";
//					ps = Main.connS.getConnection().prepareStatement(statement);
//					ps.setString(1, courseName);
				}
			}
//			System.out.println(statement);
			ps = Main.connS.getConnection().prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			return parseResults(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<ArrayList<String>> getCommentByScoreOrDept(String score, String deptName, String courseName,
			String profName) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		PreparedStatement ps = null;
//		String statement = "Select c.content, c.rate, c.Date, c2.Name as [Course Name], u.Name as [Professor Name], c.ID, c.raterName \r\n"
//				+ "from Comment c join [User] u on c.ProfessorUsername = u.Username join Course c2 on c.CourseID = c2.ID join \r\n"
//				+ "Department d on d.ID = c2.Dept ";
		String statement = "";
		int actualScore = 0;
		try {
			if (courseName != null && !courseName.isEmpty()) {
				if (score != null && !score.isEmpty()) {
					try {
						actualScore = Integer.valueOf(score);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Illegal Score entered");
						return null;
					}
					if (deptName != null && !deptName.isEmpty()) {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments5('"+ actualScore 
																		+ "', '" + deptName 
																		+ "', '" + courseName
																		+ "', '" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
//							ps.setString(2, deptName);
//							ps.setString(3, courseName);
//							ps.setString(4, profName);
						} else {
							statement = "select * from fn_showallcomments6('" + actualScore
																		+ "', '" + deptName
																		+ "', '" + courseName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
//							ps.setString(2, deptName);
//							ps.setString(3, courseName);
						}
					} else {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments7('" + actualScore
									+ "', '" + courseName
									+ "', '" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
//							ps.setString(2, courseName);
//							ps.setString(3, profName);
						} else {
							statement = "select * from fn_showallcomments8('" + actualScore
									+ "', '" + courseName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
//							ps.setString(2, courseName);
						}
					}
				} else {
					if (deptName != null) {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments9('" + deptName
									+ "', '" + courseName
									+ "', '" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, deptName);
//							ps.setString(2, courseName);
//							ps.setString(3, profName);
						} else {
							statement = "select * from fn_showallcomments10('" + deptName
									+ "', '" + courseName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, deptName);
//							ps.setString(2, courseName);
						}
					} else {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments11('" + courseName
									+ "', '" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, courseName);
//							ps.setString(2, profName);
						} else {
							statement = "select * from fn_showallcomments12('" + courseName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, courseName);
						}
					}

				}
			} else {
				if (score != null && !score.isEmpty()) {
					try {
						actualScore = Integer.valueOf(score);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Illegal Score entered");
						return null;
					}
					if (deptName != null && !deptName.isEmpty()) {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments13('" + actualScore
									+ "', '" + deptName
									+ "', '" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
//							ps.setString(2, deptName);
//							ps.setString(3, profName);
						} else {
							statement = "select * from fn_showallcomments14('" + actualScore
									+ "', '" + deptName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
//							ps.setString(2, deptName);
						}
					} else {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments15('" + actualScore
									+ "', '" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
//							ps.setString(2, profName);
						} else {
							statement = "select * from fn_showallcomments16('" + actualScore + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setInt(1, actualScore);
						}
					}
				} else {
					if (deptName != null) {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments17('" + deptName
									+ "', '" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, deptName);
//							ps.setString(2, profName);
						} else {
							statement = "select * from fn_showallcomments18('" + deptName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, deptName);
						}
					} else {
						if (profName != null && !profName.isEmpty()) {
							statement = "select * from fn_showallcomments19('" + profName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, profName);
						} else {
							statement = "select * from fn_showallcomments20('" + courseName + "')";
//							ps = Main.connS.getConnection().prepareStatement(statement);
//							ps.setString(1, courseName);
						}
					}

				}
			}
			ps = Main.connS.getConnection().prepareStatement(statement);
			System.out.println(statement);
			ResultSet rs = ps.executeQuery();
			return parseResults(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<ArrayList<String>> getCommentbyProfessor(String professorName) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		PreparedStatement ps = null;
//		String statement = "Select c.content, c.rate, c.Date, c2.Name as [Course Name], u.Name as [Professor Name], c.ID, c.raterName "
//				+ "from Comment c join [User] u on c.ProfessorUsername = u.Username join Course c2 on c.CourseID = c2.ID "
//				+ "where u.Name = ?";
		String statement = "select * from fn_showcommentsbyprof('" + professorName + "')";
		try {
			ps = Main.connS.getConnection().prepareStatement(statement);
//			ps.setString(1, professorName);

			ResultSet rs = ps.executeQuery();
			return parseResults(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private ArrayList<ArrayList<String>> parseResults(ResultSet rs) {
		try {
			ArrayList<ArrayList<String>> comment = new ArrayList<ArrayList<String>>();
			int ContentIndex = rs.findColumn("Content");
			int rateIndex = rs.findColumn("rate");
			int dateIndex = rs.findColumn("Date");
			int courseIndex = rs.findColumn("Course Name");
			int ProfIndex = rs.findColumn("Professor Name");
			int commentID = rs.findColumn("ID");
			int raterName = rs.findColumn("raterName");
			while (rs.next()) {
				ArrayList<String> re = new ArrayList<String>();
				re.add(rs.getString(ContentIndex));
				re.add(rs.getString(rateIndex));
				re.add(rs.getString(dateIndex));
				re.add(rs.getString(courseIndex));
				re.add(rs.getString(ProfIndex));
				re.add(rs.getString(commentID));
				re.add(rs.getString(raterName));

				comment.add(re);
			}

			return comment;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "An error ocurred while retrieving comments. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<ArrayList<String>>();
		}

	}
}
