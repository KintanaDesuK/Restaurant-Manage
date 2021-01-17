package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Desks")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Desk {
	 	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    private int numOfSeat;
	    
	    @Enumerated(EnumType.ORDINAL)
	    private StatusTable status;
	    
	    private int tableNum;
	    
	    @ManyToOne(fetch = FetchType.LAZY,optional=false)
	    @JoinColumn(name = "area_id", nullable = false)
	    @JsonIgnore
	    private Area area;
	    
	    @OneToMany(mappedBy="desk", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
		private Set<Order> orders;
	    
	    public Desk() {
	    	
	    }

		

		public Desk(int numOfSeat, StatusTable status, int tableNum, Area area, Set<Order> orders) {
			super();
			this.numOfSeat = numOfSeat;
			this.status = status;
			this.tableNum = tableNum;
			this.area = area;
			this.orders = orders;
		}



		public Set<Order> getOrders() {
			return orders;
		}



		public void setOrders(Set<Order> orders) {
			this.orders = orders;
		}



		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public int getNumOfSeat() {
			return numOfSeat;
		}

		public void setNumOfSeat(int numOfSeat) {
			this.numOfSeat = numOfSeat;
		}

		public StatusTable getStatus() {
			return status;
		}

		public void setStatus(StatusTable status) {
			this.status = status;
		}

		public int getTableNum() {
			return tableNum;
		}

		public void setTableNum(int tableNum) {
			this.tableNum = tableNum;
		}

		public Area getArea() {
			return area;
		}

		public void setArea(Area area) {
			this.area = area;
		}

	    
}
