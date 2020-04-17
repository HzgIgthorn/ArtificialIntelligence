package de.tobi.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleTour {
	
	private List<City> tour = new ArrayList<>();
	private double dist = 0;
	
	public SingleTour() {
		super();
		
		for(int i = 0; i < Repository.getNumberOfCities(); i++) {
			tour.add(null);
		}
	}

	public SingleTour(List<City> tour) {
		super();
		List<City> currTour = new ArrayList<City>();
		
		for(int i = 0; i < tour.size(); ++i) {
			currTour.add(null);
		}
		
		for(int i=0; i < tour.size(); ++i) {
			currTour.set(i, tour.get(i));
		}
		
		this.tour = currTour;
	}
	
	public double getDistance() {
		if(dist == 0) {
			int tourDist = 0;
			for(int i = 0; i < this.getTourSize(); ++i) {
				City fromCity = getCity(i);
				City destCity;
				
				if(i+1 < this.getTourSize())
					destCity = this.getCity(i+1);
				else
					destCity = this.getCity(0);
				tourDist += fromCity.distTo(destCity);
			}
			this.dist = tourDist;
		}
		return this.dist;
	}

	public List<City> getTour() {
		return tour;
	}

	public void generateIndividual() {
		for(int i = 0; i < Repository.getNumberOfCities(); ++i) {
			setCity(i, Repository.getCity(i));
		}
		
		Collections.shuffle(tour);
	}

	public void setCity(int i, City city) {
		this.tour.set(i, city);
		this.dist = 0;		
	}
	
	public City getCity(int pos) {
		return tour.get(pos);
	}
	
	public int getTourSize() {
		return this.tour.size();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getDistance()+"|"+this.getCity(0).toString());
		
		for(int i=1; i < this.getTourSize(); ++i) {
			builder.append(" -> "+this.getCity(i));
		}
		builder.append(" -> "+this.getCity(0).toString());
		return builder.toString();
	}
}


