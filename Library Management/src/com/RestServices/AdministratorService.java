package com.RestServices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.DbUtils.DBConnection;
import com.Pojo.AverageFine;
import com.Pojo.Book;
import com.Pojo.Reader;
import com.Pojo.Response;

@Path("/Administrator")
public class AdministratorService {
	
	@GET
	@Path("addExistingBook/libraryId/{libId}/bookISBN/{ISBN}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addExistingBook(@PathParam("libId") int libraryId, @PathParam("ISBN") String ISBN){
		
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		Response resp=new Response();
		
		try{
			int noOfCopy=0;
			String sql="Select noofcopy from bookcopy where libId=? and ISBN=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1,libraryId);
			stmnt.setString(2,ISBN);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()){
				noOfCopy=rs.getInt(1);
				sql= "update bookcopy set noofcopy=? where ISBN=? and libid=? ";
				stmnt = conn.prepareStatement(sql);
				stmnt.setInt(1, noOfCopy+1);
				stmnt.setString(2, ISBN);
				stmnt.setInt(3, libraryId);
				System.out.println(sql);
				int i=stmnt.executeUpdate();
				if(i==1)
					resp.setSuccess(true);	
				
				sql="insert into book_bookcopy(ISBN) values(?)";
				stmnt = conn.prepareStatement(sql);
				stmnt.setString(1, ISBN);
				resp.setSuccess(stmnt.execute());
			}
			else{
				sql = "insert into bookcopy(LibId,ISBN,noofCopy) values(?,?,?)";
				stmnt = conn.prepareStatement(sql);
				stmnt.setInt(1,libraryId);
				stmnt.setString(2, ISBN);
				stmnt.setInt(3, 1);
				resp.setSuccess(stmnt.execute());
				
				sql="insert into book_bookcopy(ISBN) values(?)";
				stmnt = conn.prepareStatement(sql);
				stmnt.setString(1, ISBN);
				resp.setSuccess(stmnt.execute());
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	
		return resp;
		}
	
	@GET
	@Path("addNewBook/libraryId/{libraryId}/bookISBN/{ISBN}/bookTtile/{title}/bookPublisher/{publisherName}"+
				"/bookAuthor/{authorName}/bookPublicationDate/{publicationDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewBook(@PathParam("libraryId") int libraryId, @PathParam("ISBN") String ISBN,
			@PathParam("title") String title, @PathParam("publisherName") String publisherName, @PathParam("authorName") String author,
			@PathParam("publicationDate") String publicationDate){
		
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		Response resp=new Response();
		java.sql.Date sqlPublishDate=null;
		SimpleDateFormat formatter= new SimpleDateFormat("mmddyyyy");
		Date publishDate=null;
		try {
			publishDate = formatter.parse(publicationDate);
			sqlPublishDate=new java.sql.Date(publishDate.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			int publisherId=0;
			String sql="Select * from publisher where publisherName=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1,publisherName);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()){
				publisherId=rs.getInt("publisherId");
				sql= "insert into book values(?,?,?,?) ";
				stmnt = conn.prepareStatement(sql);
				//stmnt.setInt(1, noOfCopy+1);
				stmnt.setString(1, ISBN);
				stmnt.setString(2, title);
				stmnt.setInt(3, publisherId);
				stmnt.setDate(4, sqlPublishDate);
				System.out.println(sql);
				resp.setSuccess(stmnt.execute());	
				
				sql="insert into bookCopy values(?,?,?)";
				stmnt = conn.prepareStatement(sql);
				stmnt.setInt(1, libraryId);
				stmnt.setString(2, ISBN);
				stmnt.setInt(3, 1);
				resp.setSuccess(stmnt.execute());
				
				sql="insert into book_bookCopy(ISBN) values(?)";
				stmnt = conn.prepareStatement(sql);
				stmnt.setString(1, ISBN);
				resp.setSuccess(stmnt.execute());
				
				sql="insert into author(ISBN, authorName) values(?,?)";
				stmnt = conn.prepareStatement(sql);
				stmnt.setString(1, ISBN);
				stmnt.setString(2, author);
				resp.setSuccess(stmnt.execute());
				
			}
			else{
				sql = "insert into publisher(publisherName) values(?)";
				stmnt = conn.prepareStatement(sql);
				stmnt.setString(1, publisherName);
				resp.setSuccess(stmnt.execute());
				
				sql="Select publisherId from publisher where publishername=?";
				stmnt = conn.prepareStatement(sql);
				stmnt.setString(1, publisherName);
				 rs = stmnt.executeQuery();
				 publisherId=rs.getInt(1);
				 
				 sql= "insert into book values(?,?,?,?) ";
					stmnt = conn.prepareStatement(sql);
					//stmnt.setInt(1, noOfCopy+1);
					stmnt.setString(1, ISBN);
					stmnt.setString(2, title);
					stmnt.setInt(3, publisherId);
					stmnt.setDate(4, sqlPublishDate);
					System.out.println(sql);
					resp.setSuccess(stmnt.execute());	
					
					sql="insert into bookCopy values(?,?,?)";
					stmnt = conn.prepareStatement(sql);
					stmnt.setInt(1, libraryId);
					stmnt.setString(2, ISBN);
					stmnt.setInt(3, 1);
					resp.setSuccess(stmnt.execute());
					
					sql="insert into book_bookCopy(ISBN) values(?)";
					stmnt = conn.prepareStatement(sql);
					stmnt.setString(1, ISBN);
					resp.setSuccess(stmnt.execute());
					
					sql="insert into author(ISBN, authorName) values(?,?)";
					stmnt = conn.prepareStatement(sql);
					stmnt.setString(1, ISBN);
					stmnt.setString(2, author);
					resp.setSuccess(stmnt.execute());
				 
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	
		return resp;
		}
	@GET
	@Path("/addNewReader/readerName/{readerName}/address/{address}/number/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReader(@PathParam("readerName") String readerName, @PathParam("address") String address,
			@PathParam("number") String number){
		
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		Response resp=new Response();
		
		try{
			String sql;
			sql = "insert into reader(name,address,phonenumber) values(?,?,?)";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1,readerName);
			stmnt.setString(2, address);
			stmnt.setString(3, number);
			resp.setSuccess(stmnt.execute());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	
		return resp;
		}
	
	
	@GET
	@Path("/searchBookCopyAndCheckItsStatus/bookTitle/{bookTitle}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBookCopyWithStatus(@PathParam("bookTitle") String bookTitle){
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Book> bookList=new ArrayList<Book>();
		
		
		try{
			String sql;
			String ISBN="";
			List<Integer> bookIds=new ArrayList<Integer>();
			List<String> bookTitles=new ArrayList<String>();
			sql="Select ISBN from book where title=?";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1,bookTitle );
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next())
				ISBN=rs.getString(1);
			
			sql="Select bookid, book.title from book_bookcopy, book"
					+ " where book.ISBN=? and book_bookcopy.ISBN=book.ISBN";
			stmnt=conn.prepareStatement(sql);
			stmnt.setString(1, ISBN);
			rs=stmnt.executeQuery();
			while(rs.next()){
			bookIds.add(rs.getInt(1));
			bookTitles.add(rs.getString(2));
			}
			boolean first=true;
			String ids="";
			for (int i : bookIds) {
				if (first) {
					ids += i;
					first = false;
				} else {
					ids += "," + i;
				}
			}
			List<Integer> borrowIds=new ArrayList<Integer>();
			
			sql="Select borrow.bookId, reader.name, branch.name, book.title from borrow, book_bookcopy, branch, reader, book where"
					+ " borrow.bookId=book_bookcopy.bookid and branch.libid=borrow.branchid and reader.readerid=borrow.readerid "
					+ " and borrow.bookid in("+ids+") and borrow.returndate is null and book.ISBN=book_bookCopy.ISBN";
			stmnt=conn.prepareStatement(sql);
			rs=stmnt.executeQuery();
			while(rs.next()){
				if(bookIds.contains(rs.getInt(1))){
					Book book=new Book();
					book.setBookId(rs.getInt(1));
					borrowIds.add(rs.getInt(1));
					String status="Borrowed by "+ rs.getString(2)+" from Library "+rs.getString(3);
					book.setStatus(status);
					book.setTitle(rs.getString(4));
					bookList.add(book);
				}
				
			}
			
			List<Integer> reserveIds=new ArrayList<Integer>();
			sql="Select reserve.bookId, reader.Name, book.title from reserve, reader, book, book_bookCopy where reserve.readerid=reader.readerid"
					+ " and reserve.reservedate=curdate() and reserve.bookid in ("+ids+") and reserve.bookid=book_bookcopy.bookid"
							+ " and book.ISBN=book_bookcopy.ISBN";
			
			stmnt=conn.prepareStatement(sql);
			rs=stmnt.executeQuery();
			while(rs.next()){
				if(bookIds.contains(rs.getInt(1))){
					Book book=new Book();
					book.setBookId(rs.getInt(1));
					reserveIds.add(rs.getInt(1));
					String status="Reserved by "+ rs.getString(2);
					book.setStatus(status);
					book.setTitle(rs.getString(3));
					bookList.add(book);
				}
				
			}
			for(int i=0;i<bookIds.size();i++){
				if(!borrowIds.contains(bookIds.get(i))&& !reserveIds.contains(bookIds.get(i))){
					Book book = new Book();
					book.setBookId(bookIds.get(i));
					book.setStatus("Available");
					book.setTitle(bookTitles.get(i));
					bookList.add(book);
				}
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return bookList;
	}
	
	@GET
	@Path("/getTopTenBorrowedBooks/branchName/{branchName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getToptenBorrowedBooks(@PathParam("branchName") String branchName){
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Book> bookList=new ArrayList<Book>();
		try{
			String sql;
			sql="SELECT COUNT(*) AS cnt,borrow.bookId, book.ISBN, book.Title"
					+" FROM book, book_bookcopy, borrow, branch"
					+" WHERE book.isbn=book_bookcopy.isbn" 
					+" AND book_bookcopy.bookid=borrow.bookid"
					+" AND borrow.branchId=branch.libId"
					+" AND branch.name=?"
					+" GROUP BY book.ISBN"
					+" ORDER BY cnt DESC"
					+" LIMIT 10";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1,branchName);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			while(rs.next()){
				Book book=new Book();
				book.setCount(rs.getInt(1));
				book.setBookId(rs.getInt(2));
				book.setISBN(rs.getString(3));
				book.setTitle(rs.getString(4));
				bookList.add(book);
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return bookList;
	}
	
	@GET
	@Path("/getTopTenReaders/branchName/{branchName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reader> getToptenReader(@PathParam("branchName") String branchName){
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Reader> readerList=new ArrayList<Reader>();
		try{
			String sql;
			sql="SELECT COUNT(*) AS cnt, reader.name, reader.readerid" 
					+" FROM borrow, reader, branch"
					+" WHERE borrow.readerId=reader.readerId"
					+" AND borrow.branchId=branch.LibId"
					+" AND branch.name=?"
					+" GROUP BY reader.readerId"
					+" ORDER BY cnt DESC"
					+" LIMIT 10";
			stmnt = conn.prepareStatement(sql);
			stmnt.setString(1,branchName);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			while(rs.next()){
				Reader reader=new Reader();
				reader.setCount(rs.getInt(1));
				reader.setName(rs.getString(2));
				reader.setReaderId(rs.getInt(3));
				readerList.add(reader);
				
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return readerList;
	}
	
	@GET
	@Path("/getAverageFinePerReader")
	@Produces(MediaType.APPLICATION_JSON)
	public AverageFine getAverageFinePerReader(){
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		AverageFine fine=new AverageFine();
		try{
			String sql;
			sql="Select count(*) from (Select distinct readerId from borrow) as temp";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			int count=0;
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
			sql="Select sum(fine) from borrow";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			float totalFine=0;
			rs = stmnt.executeQuery();
			if(rs.next()){
				totalFine=rs.getFloat(1);
			}
			float avgFine=totalFine/count;
			
			
			fine.setFine(avgFine);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return fine;
	}
	@GET
	@Path("/getAllBooksTitle")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getAllBooksTitle(){
		Connection conn = DBConnection.getConnection();
		java.sql.PreparedStatement stmnt = null;
		List<Book> bookList=new ArrayList<Book>();
		try{
			String sql;
			sql="Select distinct title, ISBN from book";
			stmnt = conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = stmnt.executeQuery();
			while(rs.next()){
				Book book =new Book();
				book.setTitle(rs.getString(1));
				book.setISBN(rs.getString(2));
				bookList.add(book);
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return bookList;
	}
	

}
