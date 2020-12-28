package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	    @JoinColumn
	    private Area area;
	    
	    public Desk() {
	    	
	    }

		public Desk(Integer id, int numOfSeat, StatusTable status, int tableNum, Area area) {
			super();
			this.id = id;
			this.numOfSeat = numOfSeat;
			this.status = status;
			this.tableNum = tableNum;
			this.area = area;
		}

		public Desk(int numOfSeat, StatusTable status, int tableNum, Area area) {
			super();
			this.numOfSeat = numOfSeat;
			this.status = status;
			this.tableNum = tableNum;
			this.area = area;
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
