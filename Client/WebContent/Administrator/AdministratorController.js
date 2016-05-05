angular.module('AdminApp', [])
.controller('AdminCtrl', ['$scope','$http', function ($scope,$http) {
	$scope.selectedHTML="Administrator1.html";
	$scope.selectedSearchHTML="existingBookAdd.html";
	$scope.booksByTitle=[];
	$scope.selectedBooksWithStatus=[];
	$scope.allBranches=[];
	$scope.top10Readers=[];
	$scope.top10Books=[];
	$scope.averageFine="";
	
	
	$http.get("http://localhost:8080/Library_Management/webServices/Administrator/getAllBooksTitle")
	.success(function(data){
		$scope.booksByTitle=data.Book;
		
      });
	$http.get("http://localhost:8080/Library_Management/webServices/Book/getAllBranch")
	.success(function(data){
		$scope.allBranches=data.branch;
		
      });
	$http.get("http://localhost:8080/Library_Management/webServices/Administrator/getAverageFinePerReader")
	.success(function(data){
		$scope.averageFine=data.fine;
		
      });

	$scope.addNewReader=function(readerName, readerAddress, readerNumber){
		$scope.resultBookId="";
		$scope.title="";
		$scope.ISBN="";
		$scope,book=[];
		$http.get("http://localhost:8080/Library_Management/webServices/Administrator/" +
				"addNewReader/readerName/"+readerName+"/address/"+readerAddress+"/number/"+readerNumber)
		
		.success(function(data){
			alert("Reader Added Succesfully");
			
	      });
		
		
	};
	$scope.searchBooksByTitle=function(selectedBook){
		$http.get("http://localhost:8080/Library_Management/webServices/Administrator/" +
				"searchBookCopyAndCheckItsStatus/bookTitle/"+selectedBook)
		.success(function(data){
			$scope.selectedBooksWithStatus=data.Book;
			
	      });
		
		
	};
	$scope.printTop10Borrowers=function(selectedBranch){
		$http.get("http://localhost:8080/Library_Management/webServices/Administrator/" +
				"getTopTenReaders/branchName/"+selectedBranch)
		.success(function(data){
			$scope.top10Readers=data.Reader;
			
	      });
		
		
	};
	$scope.printTop10Books=function(selectedBranch){
		$http.get("http://localhost:8080/Library_Management/webServices/Administrator/" +
				"getTopTenBorrowedBooks/branchName/"+selectedBranch)
		.success(function(data){
			$scope.top10Books=data.Book;
			
	      });
		
		
	};
	$scope.addExistingBook=function(selectedBook, selectedBranch){
		$http.get("http://localhost:8080/Library_Management/webServices/Administrator/" +
				"addExistingBook/libraryId/"+selectedBranch+"/bookISBN/"+selectedBook)
		.success(function(data){
			alert("Book Added Succesfully");
			
	      });
		
		
	};
	
	$scope.addNewBook=function(ISBN, Title,publisher,author,publicationDate,selectedBranch){
		$http.get("http://localhost:8080/Library_Management/webServices/Administrator/" +
				"addNewBook/libraryId/"+selectedBranch+"/bookISBN/"+ISBN+"/bookTtile/"+Title+"/bookPublisher/"+publisher+
				"/bookAuthor/"+author+"/bookPublicationDate/"+publicationDate.toString())
		.success(function(data){
			alert("Book Added Succesfully");
			
	      });
		
		
	};
	$scope.Quit=function(){
		 window.location ="http://localhost:8080/Client"
		
		
	};
	
	
   
}]);