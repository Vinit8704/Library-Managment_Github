package com.RestServices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.DbUtils.DBConnection;
import com.Pojo.Book;
import com.Pojo.Branch;
import com.Pojo.Publisher;
import com.Pojo.Response;

@Path("/Book")
public class BookService {

	@GET
	@Path("/searchBook/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book searchBookById(@PathParam("bookId") int bookId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		String ISBN = "";
		String title;
		Date publicationDate;
		Book book = null;
		try {

			String sql;
			sql = "Select bc.bookId, b.ISBN, b.Title, b.publicationDate from book b, book_bookcopy bc "
					+ "where b.ISBN=bc.ISBN and bc.bookid=? group by b.ISBN ";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, bookId);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if (rs.next())
				ISBN = rs.getString("ISBN");
			title = rs.getString("title");
			publicationDate = rs.getDate("publicationDate");
			book = new Book();
			book.setISBN(ISBN);
			book.setTitle(title);
			book.setPublicationDate(publicationDate);
			book.setBookId(bookId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;

	}

	@GET
	@Path("/searchBookByTitle/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> searchBookByTitle(@PathParam("title") String title) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		String ISBN = "";
		String bookTitle;
		Date publicationDate;
		int bookId;
		List<Book> bookList = new ArrayList<Book>();
		try {

			String sql;
			sql = "Select bc.bookId, b.ISBN, b.Title, b.publicationDate from book b, book_bookCopy bc"
					+ " where b.ISBN=bc.ISBN and b.title=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, title);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			while(rs.next()){
			ISBN = rs.getString("ISBN");
			bookTitle = rs.getString("title");
			publicationDate = rs.getDate("publicationDate");
			bookId = rs.getInt("bookId");
			Book book =new Book();
			book = new Book();
			book.setISBN(ISBN);
			book.setTitle(bookTitle);
			book.setPublicationDate(publicationDate);
			book.setBookId(bookId);
			bookList.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;

	}

	@GET
	@Path("/searchBookByPublisher/{publisherName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> searchBookByPublisherName(@PathParam("publisherName") String publisherName) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		String ISBN = "";
		String bookTitle;
		Date publicationDate;
		int bookId;
		List<Book> bookList=new ArrayList<Book>();
		try {

			String sql;
			sql = "Select bc.bookId, b.ISBN, b.Title, b.publicationDate from book b, publisher p, book_bookcopy bc "
					+ " where b.publisherId=p.publisherId and bc.ISBN=b.ISBN and p.publisherName=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, publisherName);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			while(rs.next()){
			ISBN = rs.getString("ISBN");
			bookTitle = rs.getString("title");
			publicationDate = rs.getDate("publicationDate");
			bookId = rs.getInt("bookId");
			Book book =new Book();
			book.setISBN(ISBN);
			book.setTitle(bookTitle);
			book.setPublicationDate(publicationDate);
			book.setBookId(bookId);
			bookList.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;

	}

	@GET
	@Path("/bookCheckOutSelection/{branchId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> bookCheckOut(@PathParam("branchId") int branchId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;

		try {

			String sql;
			sql = "SELECT bcp.bookid FROM book_bookcopy bcp, branch br, bookcopy copy"
					+ " WHERE br.libid=copy.libid AND bcp.ISBN=copy.ISBN" + " AND br.libid=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, branchId);
			System.out.println(sql);

			List<Integer> bookIds = new ArrayList<Integer>();

			ResultSet rs = stmnt.executeQuery();
			while (rs.next()) {
				bookIds.add(rs.getInt(1));
			}
			List<Integer> borrowBookIds = new ArrayList<Integer>();
			sql = "Select distinct bookId from borrow where returndate is null ";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			// List<Integer> bookIds=new ArrayList<Integer>();

			rs = stmnt.executeQuery();
			while (rs.next()) {
				borrowBookIds.add(rs.getInt(1));
			}
			bookIds.removeAll(borrowBookIds);
			List<Integer> reserveBookIDs = new ArrayList<Integer>();
			sql = "Select distinct bookid from reserve where reservedate = curdate()";
			System.out.println(sql);
			stmnt = conn.prepareStatement(sql);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				reserveBookIDs.add(rs.getInt(1));
			}
			bookIds.removeAll(reserveBookIDs);
			String ids = "";
			List<Book> resultBooks = new ArrayList<Book>();
			boolean first = true;
			for (int i : bookIds) {
				if (first) {
					ids += i;
					first = false;
				} else {
					ids += "," + i;
				}
			}
			sql = "Select cpy.bookId, b.ISBN, b.title, b.publicationdate from book b , book_bookcopy cpy where cpy.ISBN=b.ISBN "
					+ " and cpy.bookid in (" + ids + ")";
			System.out.println(sql);
			stmnt = conn.prepareStatement(sql);
			// stmnt.setString(1,ids);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt(1));
				b.setISBN(rs.getString(2));
				b.setTitle(rs.getString(3));
				b.setPublicationDate(rs.getDate(4));
				resultBooks.add(b);
			}

			return resultBooks;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Path("/bookCheckOut/branchId/{branchId}/bookId/{bookId}/readerId/{readerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkOut(@PathParam("branchId") int branchId, @PathParam("bookId") int bookId,
			@PathParam("readerId") int readerId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		boolean result=false;
		Response resp=new Response();

		try {

			String sql;
			sql = "insert into borrow (bookid, readerid, branchid, borrowdate)"
					+ " values(?,?,?,curdate())";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, bookId);
			stmnt.setInt(2, readerId);
			stmnt.setInt(3, branchId);
			System.out.println(sql);

			//List<Integer> bookIds = new ArrayList<Integer>();
            
			 resp.setSuccess(stmnt.execute());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
		

	}
	
	@GET
	@Path("/bookReturnSelection/readerId/{readerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> bookReturnSelection(@PathParam("readerId") int readerId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Book> bookList=new ArrayList<Book>();

		try {
			String sql;
			sql = "Select cpy.bookId, b.ISBN, b.title, b.publicationdate from book b , book_bookcopy cpy, borrow br where cpy.ISBN=b.ISBN "
					+ " and br.bookid=cpy.bookid and br.readerid=? and br.returndate is null";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, readerId);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt(1));
				b.setISBN(rs.getString(2));
				b.setTitle(rs.getString(3));
				b.setPublicationDate(rs.getDate(4));
				bookList.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookList;

	}
	
	@GET
	@Path("/bookReturn/readerId/{readerId}/bookId/{bookId}")
	public Response bookReturn(@PathParam("readerId") int readerId, @PathParam("bookId") int bookId ) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		Date borrowdate=null;
		int days=0;
		float fine=0;
		Response resp=new Response();

		try {
			String sql;
			sql = "Select borrowdate from borrow where bookid=? and readerid=? and returndate is null ";
			//int day=
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, bookId);
			stmnt.setInt(2, readerId);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next())
				borrowdate=rs.getDate(1);
			if(borrowdate!=null){
				long diff=System.currentTimeMillis()-borrowdate.getTime();
				days=(int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			}
			if(days>20){
				fine=(float) ((days-20)*0.20);
			}
			sql= "update borrow set returndate=curdate(), fine=? where bookid=? and readerid=? ";
			stmnt = conn.prepareStatement(sql);
			stmnt.setFloat(1, fine);
			stmnt.setInt(2, bookId);
			stmnt.setInt(3, readerId);
			System.out.println(sql);
			int i=stmnt.executeUpdate();
			if(i==1)
				resp.setSuccess(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resp;

	}
	
	@GET
	@Path("/bookReserveSelection")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> bookReserve() {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;

		try {

			String sql;
			sql = "SELECT bcp.bookid FROM book_bookcopy bcp";
			stmnt = conn.prepareStatement(sql);
			//stmnt.setString(1, branchName);
			System.out.println(sql);

			List<Integer> bookIds = new ArrayList<Integer>();

			ResultSet rs = stmnt.executeQuery();
			while (rs.next()) {
				bookIds.add(rs.getInt(1));
			}
			List<Integer> borrowBookIds = new ArrayList<Integer>();
			sql = "Select distinct bookId from borrow where returndate is null ";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			// List<Integer> bookIds=new ArrayList<Integer>();

			rs = stmnt.executeQuery();
			while (rs.next()) {
				borrowBookIds.add(rs.getInt(1));
			}
			bookIds.removeAll(borrowBookIds);
			List<Integer> reserveBookIDs = new ArrayList<Integer>();
			sql = "Select distinct bookid from reserve where reservedate = curdate()";
			System.out.println(sql);
			stmnt = conn.prepareStatement(sql);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				reserveBookIDs.add(rs.getInt(1));
			}
			bookIds.removeAll(reserveBookIDs);
			String ids = "";
			List<Book> resultBooks = new ArrayList<Book>();
			boolean first = true;
			for (int i : bookIds) {
				if (first) {
					ids += i;
					first = false;
				} else {
					ids += "," + i;
				}
			}
			sql = "Select cpy.bookId, b.ISBN, b.title, b.publicationdate from book b , book_bookcopy cpy where cpy.ISBN=b.ISBN "
					+ " and cpy.bookid in (" + ids + ")";
			System.out.println(sql);
			stmnt = conn.prepareStatement(sql);
			// stmnt.setString(1,ids);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt(1));
				b.setISBN(rs.getString(2));
				b.setTitle(rs.getString(3));
				b.setPublicationDate(rs.getDate(4));
				resultBooks.add(b);
			}

			return resultBooks;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	@GET
	@Path("/bookReserve/bookId/{bookId}/readerId/{readerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bookReserve(@PathParam("bookId") int bookId, @PathParam("readerId") int readerId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		Response resp=new Response();

		try {

			String sql;
			sql = "insert into reserve (bookid, readerid, reservedate)"
					+ " values(?,?,curdate())";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, bookId);
			stmnt.setInt(2, readerId);
			System.out.println(sql);

			//List<Integer> bookIds = new ArrayList<Integer>();

			 resp.setSuccess(stmnt.execute());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
		

	}
	
	@GET
	@Path("/computeFine/readerId/{readerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> computeFine(@PathParam("readerId") int readerId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		//Boolean result=false;
		List<Book> bookList=new ArrayList<Book>();

		try {

			String sql;
			sql = "Select borrow.bookid, borrowdate, book.title from borrow, book_bookcopy, book where readerid=?"
					+ " and borrow.bookid=book_bookcopy.bookId and book.ISBN=book_bookcopy.ISBN";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, readerId);
			System.out.println(sql);
			ResultSet rs=stmnt.executeQuery();
			while(rs.next()){
				Book book =new Book();
				book.setBookId(rs.getInt(1));
				float fine= fineCalculation(rs.getDate(2));
				book.setFine(fine);
				book.setBorrowDate(rs.getDate(2));
				book.setTitle(rs.getString(3));
				
				bookList.add(book);
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;
		

	}

	private float fineCalculation(Date date) {
	   float fine =0;
	   int days=0;
	   		long diff=System.currentTimeMillis()-date.getTime();
			days=(int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		if(days>20){
			fine=(float) ((days-20)*0.20);
		}
		return fine;
	}
	
	@GET
	@Path("/ReserveBookStatus/readerId/{readerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getReserveBookStatus(@PathParam("readerId") int readerId) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		//Boolean result=false;
		List<Book> bookList=new ArrayList<Book>();
		List<Integer> reservedIds=new ArrayList<Integer>();
		List<Integer> notavailableIds=new ArrayList<Integer>();

		try {

			String sql;
			sql = "Select bookid from reserve where readerid=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, readerId);
			System.out.println(sql);
			ResultSet rs=stmnt.executeQuery();
			while(rs.next()){
				reservedIds.add(rs.getInt(1));
			}
			
			sql="Select bookid from borrow where returndate is null";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			 rs=stmnt.executeQuery();
			while(rs.next()){
				notavailableIds.add(rs.getInt(1));
			}
			sql="Select bookid from reserve where reservedate=curdate()";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			 rs=stmnt.executeQuery();
			while(rs.next()){
				notavailableIds.add(rs.getInt(1));
			}
			String ids = "";
			boolean first = true;
			for (int i : reservedIds) {
				if (first) {
					ids += i;
					first = false;
				} else {
					ids += "," + i;
				}
			}
			if(ids.equals("")){
				return bookList;
			}
			else{
			sql = "Select cpy.bookId, b.ISBN, b.title, b.publicationdate from book b , book_bookcopy cpy where cpy.ISBN=b.ISBN "
					+ " and cpy.bookid in (" + ids + ")";
			}
			System.out.println(sql);
			stmnt = conn.prepareStatement(sql);
			// stmnt.setString(1,ids);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setBookId(rs.getInt(1));
				if(notavailableIds.contains(rs.getInt(1))){
					b.setStatus("Not Available");
				}
				else{
					b.setStatus("Available");
				}
				b.setISBN(rs.getString(2));
				b.setTitle(rs.getString(3));
				b.setPublicationDate(rs.getDate(4));
				bookList.add(b);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;
		

	}
	
	@GET
	@Path("/getAllPublishers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Publisher> getAllPublisher() {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Publisher> publisherList=new ArrayList<Publisher>();
		//Boolean result=false;
		try {

			String sql;
			sql = "Select publisherId, publisherName from publisher";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs=stmnt.executeQuery();
			while(rs.next()){
				Publisher publisher=new Publisher();
				publisher.setPublisherId(rs.getInt(1));
				publisher.setPublisherName(rs.getString(2));
				publisherList.add(publisher);
			}
			
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return publisherList;
		
		
		
	}
	
	@GET
	@Path("/getAllBooksbyPublisher/publisherName/{publisherName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getAllBooksbyPublisher(@PathParam("publisherName") String publisherName) {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Book> bookList=new ArrayList<Book>();
		//Boolean result=false;
		try {

			String sql;
			sql = "Select bookId, title, book.ISBN from book, publisher, book_bookcopy where book.publisherid=publisher.publisherid"
					+ " and book_bookcopy.ISBN=book.ISBN and publisherName=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1, publisherName);
			System.out.println(sql);
			ResultSet rs=stmnt.executeQuery();
			while(rs.next()){
				Book book=new Book();
				book.setBookId(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setISBN(rs.getString(3));
				bookList.add(book);
				
			}
			
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return bookList;
		
		
		
	}
	
	@GET
	@Path("/getAllBranch")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Branch> getAllBranch() {
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Branch> branchList=new ArrayList<Branch>();
		//Boolean result=false;
		try {

			String sql;
			sql = "Select * from branch";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs=stmnt.executeQuery();
			while(rs.next()){
			  Branch branch=new Branch();
			  branch.setLibraryId(rs.getInt("libid"));
			  branch.setName(rs.getString("name"));
			  branch.setLocation(rs.getString("location"));
			  branchList.add(branch);
			}
			
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return branchList;
		
		
		
	}
	

	
	
	
	

}
