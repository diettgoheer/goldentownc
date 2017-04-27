package com.ss.goldentown.service.task;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ss.goldentown.domain.Person;
import com.ss.goldentown.domain.World;
import com.ss.goldentown.service.NameService;
import com.ss.goldentown.service.PersonService;
import com.ss.goldentown.service.WorldService;

@Component
@Configurable
@EnableScheduling
public class PersonTask {
	private double worldValue = 10000;
	private double wholeWorldValue = 1000000;
	
	private double oneAge = 30;
	private final double baseWorldValue = 5000;
	private final double midAge =15;
	private double baseValue = 1;
	private final double growRate = 1.05;
	private final double legacyRate = 0.382;
	private final double breedRate = 0.1;
	private double mValue = 0;
	private List<Person> gPersons;
/*	private List<Person> newPersons;*/
	private List<Person> forgettenPersons;
	private int g = 0;
	private final Logger log = LoggerFactory.getLogger(PersonTask.class);
	@Inject
	private PersonService personService;
	@Inject
	private WorldService worldService;

	
	@Scheduled(cron="0/2 * *  * * ? ")
	public void grow(){
		log.debug("Grow Start");
		if(gPersons==null)
			gPersons = personService.findAll();
		/*if(newPersons==null)
			newPersons = new ArrayList<Person>();
		*/
		if(forgettenPersons==null)
		forgettenPersons = new ArrayList<Person>();
		log.debug("There is "+ gPersons.size());
		
		if(g%12==1){
			if(gPersons!=null&&gPersons.size()!=0){
				//personService.batchDeleteAll();
				personService.batchUpdate(gPersons);
			}
			setWorld(gPersons);
			gPersons = personService.findAll();
		}
		g++;
		
		if(gPersons.size()==0){
			Person son = new Person();
			son.setSurname(NameService.getSurname("轩辕"));
			son.setName(NameService.getFixedLengthChinese(son.getSurname(), 3));
		    son.setAge((double) 0);
		    son.setBirthday(ZonedDateTime.now());
		    son.setValue(baseValue);
		    son.setLastValue(son.getValue());
		    son.setGeneration((double)0);
		    gPersons.add(son);
		    personService.save(son);
		}
		boolean forgetten = true;
		/*long bid = gPersons.get(gPersons.size()-1).getId();
		int did = 0;*/
		double gp  = getGP(gPersons);
		for(int i = 0;i<gPersons.size();i++){
			Person person =gPersons.get(i);
			if(gPersons.get(i).isIsDead()==null||gPersons.get(i).isIsDead()==false){
				forgetten = false;
				//gPersons.get(i).setId(null);
				double pp = getPP(person);
				if(Math.random()>gp&&Math.random()>pp){
					growPerson(gp,person);
				}
				else{
					if(person.getValue()>500&&Math.random()>0.8){
						person.setValue(person.getValue()*0.8-100);
						growPerson(gp,person);
					}
					else{
						person.setIsDead(true);
						person.setDeathday(ZonedDateTime.now());
						sharpValue(person.getName(),person.getValue(),i);
					}
				}
			}
			if(gPersons.get(i).isIsDead()&&forgetten)
				forgettenPersons.add(gPersons.get(i));
		}
		
		
		/*personService.batchDelete(forgettenPersons);
		gPersons.removeAll(forgettenPersons);
		deathCount = deathCount + forgettenPersons.size();
		forgettenPersons = new ArrayList<Person>();*/
		log.debug("Grow End");
	}
	
	private void growPerson(double gp,Person person){		
			person.setValue(person.getValue()*growRate);
			person.setAge(person.getAge()+1);
			double bp = getBP(person);
			if(Math.random()<bp){
				Person son = new Person();
				//son.setId((long)((int)bid+did));
				son.setSurname(NameService.getSurname(person.getSurname()));
				son.setName(NameService.getFixedLengthChinese(son.getSurname(), 3));
				son.setFatherName(person.getName());
				son.setAge((double) 0);
		    	son.setBirthday(ZonedDateTime.now());
		    	son.setValue(baseValue+breedRate*person.getValue());
		    	son.setLastValue(son.getValue());
		    	son.setGeneration(person.getGeneration()+1);
		    	gPersons.add(son);
			}
			
		
	}
	
	public double getGP(List<Person> gPersons){
		double gp = 0;
		mValue = mValue*breedRate;
		for(int i = 0;i<gPersons.size();i++){
			if(gPersons.get(i).isIsDead()==null||gPersons.get(i).isIsDead()==false)
			mValue = gPersons.get(i).getValue()+mValue;
		}
		gp = Math.log(mValue)/Math.log(worldValue)-1;
		gp = Math.min(gp, legacyRate);
		if(gp>0)
		    return gp;
		else
			return 0;
	}
	
	public double getPP(Person person){
		double pp = 0;
		pp = (Math.log(person.getAge())/Math.log(oneAge) -1)/1.5;
		if(pp>0)
		    return pp;
		else
			return 0;
	}
	
	public double getBP(Person person){
		double p = 0;
		p = (Math.log(person.getAge())/Math.log(midAge) -1)/2;
		if(person.getAge()>oneAge)
			p=p*oneAge/person.getAge();
		if(p>0)
		    return p;
		else
			return 0;
	}
	

	
	public void sharpValue(String name,double fValue,int start){
		int j=1;
		for(int i = start;i<gPersons.size();i++){
			
			if(gPersons.get(i).getFatherName()!=null&&gPersons.get(i).getFatherName()==name){
				double value =fValue*Math.exp(j*Math.log(legacyRate));
				if(gPersons.get(i).isIsDead())
					sharpValue(gPersons.get(i).getName(),value,i);
				else
					gPersons.get(i).setValue(value+gPersons.get(i).getValue());
				j++;
			}
		}
	
	}
	private void setWorld(List<Person> persons){
		World world = new World();
		double personCount = 0;
		double personsAge = 0;
		double personsValue = 0;
		double averageAge = 0;
		double averageValue = 0;
		double maxAge = 0;
		double maxValue = 0;
		double maxGeneration = 0;
		double deathCount = 0;
		double birthCount = 0;
		for(int i = 0;i<persons.size();i++){
			Person person = persons.get(i);
			if(person.isIsDead())
				deathCount++;
			if(person.getId()==null)
				birthCount++;
			if(person.isIsDead()==null||person.isIsDead()==false){
				personsValue = person.getValue()+personsValue;
				personsAge = person.getAge() +personsAge;
				if(person.getAge()>maxAge)
					maxAge = person.getAge();
				if(person.getValue()>maxValue)
					maxValue = person.getValue();
				
				if(person.getGeneration()>maxGeneration)
					maxGeneration = person.getGeneration();
				personCount++;
			}
		}
		averageValue = personsValue/personCount;
		averageAge = personsAge/personCount;
		world.setMaxGeneration(maxGeneration);
		world.setBirthCount(birthCount);
		world.setDeathCount(deathCount);
		world.setPersonCount(personCount);
		world.setAverageAge(averageAge);
		world.setAverageValue(averageValue);
		world.setMaxAge(maxAge);
		world.setMaxValue(maxValue);
		world.setWorldValue(worldValue); 
		worldValue = Math.min((Math.random()+0.3)*worldValue+mValue*breedRate+baseWorldValue,wholeWorldValue);
		world.setWorldAge(oneAge); 
		world.setMidAge(midAge); 
		world.setBaseValue(baseValue); 
		/*if(averageValue/3>baseValue)
			baseValue = averageValue/3;*/
		world.setGrowRate(growRate);
		world.setLegacyRate(legacyRate);
		world.setBreedRate(breedRate);
		world.time(ZonedDateTime.now());
		worldService.save(world);
	}
	
}
