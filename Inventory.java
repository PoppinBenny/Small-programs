package HW;

//@author jwang242
public class Inventory {  //large class, split prompts and functions
	private String title,genre;
	private double rentalPrice,salePrice;
	private boolean onSale=false,isCurrentlyRented=false;
	private int numberOfTimesRented=0;
	private Inventory next;
	
	
	public Inventory(String title,String genre,double rentalPrice,Inventory next){
		this.title=title;
		this.genre=genre;
		this.rentalPrice=rentalPrice;
		this.next=next;
	}
	
	public static void main(String[] args){
		Inventory dvdList=null; //better to make a global variable
		printInterface();           //duplicate code 2
		int input=TextIO.getlnInt();
		operate(dvdList,input);
		System.exit(0);
	}
	
	public void setSalePrice(int price){
		salePrice=price;
	}
	
	
	public static void operate(Inventory dvdList, int input){ //function too long, refactor each case
		//too many System.out.println (message chain), write a composite function to make it more concise or use import
		do{
			if(input==1){ 
				System.out.println("Type in the DVD title you want to add:");
				String title=TextIO.getln();
				System.out.println("Choose the genre of this DVD:"+"\n"+"Comedy, Drama, Documentary, Horror, Romance, Musical");
				String genre=TextIO.getln();
				while(!genre.equals("Comedy")&&!genre.equals("Drama")&&!genre.equals("Documentary")&&
						!genre.equals("Horror")&&!genre.equals("Romance")&&!genre.equals("Musical"))  //Duplicate code 1
				{
				    System.out.println("The genre you chose is invalid. Please choose again:");
				    genre=TextIO.getln();
				}
				System.out.println("Please set its rentalPrice:");
				int rentalPrice=TextIO.getlnInt();
				if(rentalPrice<=0){
					System.out.println("Please enter a valid number:");
					rentalPrice=TextIO.getlnInt();
				}
				while(dvdList!=null&&dvdList.find(title)){ //don't necessarily need "!=null"
					System.out.println("The title has already been in the inventory, type in another title:");
					title=TextIO.getln();
				}
				if(dvdList!=null){
				    Inventory newDVD=new Inventory(title,genre,rentalPrice,null);
				    dvdList=dvdList.add(dvdList,newDVD);
				}
				if(dvdList==null)
					dvdList=new Inventory(title,genre,rentalPrice,null);
			  }
			
			if(input==2){
				System.out.println("Type in the DVD you want to remove:");
				String title=TextIO.getln();
				if(!dvdList.find(title))
					System.out.println("The DVD you choose is not in the store.");
				else if(dvdList.find(title))
					dvdList=dvdList.remove(dvdList,title);
			}
				
			if(input==3){
				if(dvdList==null)
					System.out.println("There's no DVD in the inventory now.");
				else dvdList.printInventory(dvdList);
			}
			
			if(input==4){
				System.out.println("Type in the DVD you want its sale status to change:");
				String title=TextIO.getln();
				if(!dvdList.find(title)){
					System.out.println("The DVD you choose is not in the store. Type again:");
					title=TextIO.getln();
				}
				else if(dvdList.find(title))
					dvdList.onOffSale(title);
			}
			
			if(input==5){
				System.out.println("Choose the genre you want the inventory to display:");
				String genre=TextIO.getln();
				while(!genre.equals("Comedy")&&!genre.equals("Drama")&&!genre.equals("Documentary")&&
						!genre.equals("Horror")&&!genre.equals("Romance")&&!genre.equals("Musical")){ //Duplicate code 1
					System.out.println("The genre you choose is not valid. Type again:");
					genre=TextIO.getln();
				}
					dvdList.displayGenre(genre,dvdList);
			}
			
			if(input==6){
				dvdList.printInventory(dvdList);
				System.out.println("Which one do you want to rent?");
				String title=TextIO.getln();
				if(!dvdList.find(title))
					System.out.println("The DVD you choose is not available.");
				else if(dvdList.find(title)){
					double price=dvdList.findRent(title);
					dvdList.rent(title);
					System.out.println("Are you sure you would like us to deduct $"+price
							+" from your credit card?(Y/N)");
					boolean answer=TextIO.getBoolean();
				}
			}
			System.out.println("Press any key to go back to menu.");
			String press=TextIO.getln();
			printInterface();     //duplicate code 2
			input=TextIO.getlnInt();
			}
			while(input>0&&input<7);
	}
	
	public static void printInterface(){ //too many System calls, shrink all calls down to one
		System.out.println("Welcome to the DVD Store! Select an option below:");
		System.out.println();
		System.out.println("1) add an DVD");
		System.out.println("2) remove an DVD");
		System.out.println("3) display inventory");
		System.out.println("4) on sale/off sale");
		System.out.println("5) display genre");
		System.out.println("6) rent DVD");
		System.out.println("7) quit");
		System.out.println();
		System.out.println("Select an option above:");
	}
	
	public Inventory add(Inventory list,Inventory newDVD){
		if(list==null||newDVD.title.compareTo(list.title)<0)
			return new Inventory(newDVD.title,newDVD.genre,newDVD.rentalPrice,
					list);
		else{
			list.next=add(list.next, newDVD);
			return list;
		}
	}
	
	public Inventory remove(Inventory list,String title){
		if(list==null)
			return null;
		if(title.equals(list.title))
			return list.next;
		else list.next=remove(list.next,title);
		return list;
	}
	
	public boolean find(String title){
		if(title.equals(this.title))
			return true;
		if(next==null)
			return false;
		else return next.find(title);
	}
	
	public void printInventory(Inventory list){
		if(list==null){
			System.out.println("There's no DVD in the inventory now.");
			return;
		}
		if(isCurrentlyRented)
			System.out.println("DVD Title: "+title+" (Rented)"); //duplicate code
		if(!isCurrentlyRented)
		    System.out.println("DVD Title: "+title);
		if(!isCurrentlyRented&&!onSale&&rentalPrice!=0)
			System.out.println("Genre: "+genre+"\n"+"Number Of Times Rented: "+numberOfTimesRented //duplicate code
					+"\n"+"Price: $"+rentalPrice+"\n");
		if(!isCurrentlyRented&&onSale&&salePrice!=0)
			System.out.println("On Sale!"+"\n"+"Genre: "+genre+"\n"+"Number Of Times Rented: "
		            +numberOfTimesRented+"\n"+"Price: $"+salePrice+"\n");
		if(!isCurrentlyRented&&onSale&&salePrice==0)
			System.out.println("Genre: "+genre+"\n"+"Number Of Times Rented: "+numberOfTimesRented
					+"\n"+"Currently Free!"+"\n");
		if(next==null){return;}
		next.printInventory(list);
	}
	
	public void onOffSale(String title){
		int price;
		if(title.equals(this.title)){
				onSale=!onSale;
		        if(onSale){
					System.out.println("Please set its salePrice:");
					price=TextIO.getlnInt();
					while(price>=rentalPrice||price<0){
						System.out.println("Sale Price must be positive and lower than Rental Price. Type again:");
					    price=TextIO.getlnInt();
					}
					setSalePrice(price);
					return;
		        }
		        return;
		}
		next.onOffSale(title);
	}
	
	public void displayGenre(String genre,Inventory list){
		if(list==null){
			System.out.println("There's no DVD in the inventory now.");
			return;
		}
		if(this.genre.equals(genre)){
			if(isCurrentlyRented)
				System.out.println("DVD Title: "+title+" (Rented)");
			if(!isCurrentlyRented)
			    System.out.println("DVD Title: "+title);
			if(!onSale&&rentalPrice!=0)
				System.out.println("Genre: "+genre+"\n"+"Number Of Times Rented: "+numberOfTimesRented //duplicate code
						+"\n"+"Price: $"+rentalPrice+"\n");
			if(onSale&&salePrice!=0)
				System.out.println("On Sale!"+"\n"+"Genre: "+genre+"\n"+"Number Of Times Rented: "
			            +numberOfTimesRented+"\n"+"Price: $"+salePrice+"\n");
			if(onSale&&salePrice==0)
				System.out.println("Genre: "+genre+"\n"+"Number Of Times Rented: "+numberOfTimesRented
						+"\n"+"Currently Free!"+"\n");
		}
		if(next==null){return;}
		next.displayGenre(genre,list);
	}
	
	public double findRent(String title){
		if(title.equals(this.title)){
			if(!isCurrentlyRented&&!onSale)
				return rentalPrice;
			if(!isCurrentlyRented&&onSale)
				return salePrice;
		}
		return next.findRent(title);
	}
	
	public void rent(String title){
		if(title.equals(this.title)){
		    numberOfTimesRented++;
		    isCurrentlyRented=true;
		    return;
		}
		next.rent(title);
	}
	
}
