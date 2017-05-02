package com.ss.goldentown.service.task;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisNoOpBindingRegistry;
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
	private double wholeWorldValue = 100000;
	
	private double oneAge = 24;
	private double baseWorldValue = 4000;
	private double midAge =12;
	private double baseValue = 1;
	private double growRate = 1.03;
	private double legacyRate = 0.382;
	private double breedRate = 0.1;
	private double mValue = 0;
	private double worldCount = 1;
	private List<Person> gPersons;
/*	private List<Person> newPersons;*/
	//private List<Person> forgettenPersons;
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
		/*if(forgettenPersons==null)
		forgettenPersons = new ArrayList<Person>();*/
		log.debug("There is "+ gPersons.size());
		
		if(g%12==1){
			if(gPersons!=null&&gPersons.size()!=0){
				//personService.batchDeleteAll();
				personService.batchUpdate(gPersons);
			}
			if(gPersons!=null&&gPersons.size()!=0)
			setWorld(gPersons);
			gPersons = personService.findAll();
		}
		g++;
		
		if(gPersons.size()==0){
			initWorld();
			init();			
		}
		
		//boolean forgetten = true;
		/*long bid = gPersons.get(gPersons.size()-1).getId();
		int did = 0;*/
		double gp  = getGP(gPersons);
		if(gPersons.size()>50000){
			gp=0.6;
			initWorld();
			worldCount++;
		}
		for(int i = 0;i<gPersons.size();i++){
			Person person =gPersons.get(i);
			if(gPersons.get(i).isIsDead()==null||gPersons.get(i).isIsDead()==false){
				//forgetten = false;
				//gPersons.get(i).setId(null);
				double mGp = gp/(person.getGene1()+500)*500/(person.getGene6()+3000)*3000;
				double pp = getPP(person)/(person.getGene3()+500)*500/(person.getGene6()+3000)*3000;
				if(Math.random()>mGp&&Math.random()>pp){
					growPerson(mGp,person);
				}
				else{
					
					if(person.getValue()>5&&Math.random()>0.9){
						person.setValue(person.getValue()*0.9-1);
						growPerson(mGp,person);   
					}
					else if(person.getValue()>50&&Math.random()>0.9){
						person.setValue(person.getValue()*0.9-10);
						growPerson(mGp,person);
					}
					else if(person.getValue()>500&&Math.random()>0.9){
						person.setValue(person.getValue()*0.9-100);
						growPerson(mGp,person);
					}
					else if(person.getValue()>5000&&Math.random()>0.9){
						person.setValue(person.getValue()*0.9-1000);
						growPerson(mGp,person);
					}
					else if(person.getValue()>50000&&Math.random()>0.9){
						person.setValue(person.getValue()*0.9-10000);
						growPerson(mGp,person);
						
					}
					else{
						person.setIsDead(true);
						person.setDeathday(ZonedDateTime.now());
						sharpValue(person.getName(),person.getValue(),i);
					}
				}
			}
		/*	if(gPersons.get(i).isIsDead()&&forgetten)
				forgettenPersons.add(gPersons.get(i));*/
		}
		
		
		/*personService.batchDelete(forgettenPersons);
		gPersons.removeAll(forgettenPersons);
		deathCount = deathCount + forgettenPersons.size();
		forgettenPersons = new ArrayList<Person>();*/
		log.debug("Grow End");
	}
	
	private void init(){
		g=0;
		Person son = new Person();
		son.setSurname(NameService.getSurname("轩辕"));
		son.setName(NameService.getFixedLengthChinese(son.getSurname(), 3));
	    son.setAge((double) 0);
	    son.setBirthday(ZonedDateTime.now());
	    son.setValue(baseValue);
	    son.setLastValue(son.getValue());
	    son.setGeneration((double)0);
	    son.setGender(0);
	    son.setGene1((double)100);
	    son.setGene2((double)100);
	    son.setGene3((double)50);
	    son.setGene4((double)50);
	    son.setGene5((double)50);
	    son.setGene6((double)100);
	    son.setMotherName(NameService.getFixedLengthChinese("", 3));
	    gPersons.add(son);
	    personService.save(son);
		Person son2 = new Person();
		son2.setSurname(NameService.getSurname("蚩尤"));
		son2.setName(NameService.getFixedLengthChinese(son2.getSurname(), 3));
		son2.setMotherName(NameService.getFixedLengthChinese("", 3));
	    son2.setAge((double) 0);
	    son2.setBirthday(ZonedDateTime.now());
	    son2.setValue(baseValue);
	    son2.setLastValue(son.getValue());
	    son2.setGeneration((double)0);
	    son2.setGene1((double)50);
	    son2.setGene2((double)50);
	    son2.setGene3((double)100);
	    son2.setGene4((double)100);
	    son2.setGene5((double)100);
	    son2.setGene6((double)50);
	    gPersons.add(son2);
	    personService.save(son2);
	    
	}
	
	private void initWorld(){
		worldValue = Math.random()*worldValue+5000;		
		oneAge = Math.random()*12+oneAge;
		baseWorldValue = Math.random()*2000+baseWorldValue;
		midAge =oneAge/2;
		baseValue = Math.random()*4+baseValue;
		growRate = 1+0.03*Math.random()+0.005;
		legacyRate = 0.368+0.1*Math.random();
		breedRate = 0.05+0.1*Math.random();
	}
	
	private void growPerson(double gp,Person person){
			
			person.setValue(person.getValue()*(growRate-0.015+0.01*Math.random()+0.01*Math.random()*(person.getGene2()+400)/400*(person.getGene5()+400)/400)*(person.getGene6()+4000)/4000);
			
			person.setAge(person.getAge()+1);
			double bp = getBP(person)*(person.getGene4()+700)/700*(person.getGene6()+5000)/5000;
			if(Math.random()<bp){
				Person son = new Person();
				//son.setId((long)((int)bid+did));
				son.setSurname(NameService.getSurname(person.getSurname()));
				son.setName(NameService.getFixedLengthChinese(son.getSurname(), 3));
				son.setFatherName(person.getName());
				son.setMotherName(NameService.getFixedLengthChinese("", 3));
				son.setAge((double) 0);
		    	son.setBirthday(ZonedDateTime.now());
		    	son.setValue(baseValue+breedRate*person.getValue());
		    	son.setLastValue(son.getValue());
		    	son.setGeneration(person.getGeneration()+1);
			    son.setGene1(inherit(person.getGene1()));
			    son.setGene2(inherit(person.getGene2()));
			    son.setGene3(inherit(person.getGene3()));
			    son.setGene4(inherit(person.getGene4()));
			    son.setGene5(inherit(person.getGene5()));
			    son.setGene6(inherit(person.getGene6()));
		    	gPersons.add(son);
			}
			
		
	}
	
	public double inherit(double gene){
		double g = 0;
		int j = 50;
		int k = 5;
		g=Math.max(1, Math.min(120,(gene*j+50)/(j+1)+Math.random()*k-k/2+k*0.05));
		return g;
	}
	
	public double getGP(List<Person> gPersons){
		double gp = 0;
		mValue = mValue*breedRate;
		for(int i = 0;i<gPersons.size();i++){
			if(gPersons.get(i).isIsDead()==null||gPersons.get(i).isIsDead()==false)
			mValue = gPersons.get(i).getValue()+mValue;
		}
		gp = (Math.log(mValue)/Math.log(worldValue)-1)/7;
		gp = Math.min(gp, legacyRate/2);
		if(gp>0)
		    return gp;
		else
			return 0;
	}
	
	public double getPP(Person person){
		double pp = 0;
		pp = (Math.log(person.getAge())/Math.log(oneAge) -1)/2;
		if(pp>0)
		    return pp;
		else
			return 0;
	}
	
	public double getBP(Person person){
		double p = 0;
		p = (Math.log(person.getAge())/Math.log(midAge) -1)/3;
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
		worldValue = Math.min((Math.random()/2+0.53)*worldValue+mValue*breedRate+baseWorldValue,wholeWorldValue*(Math.abs(Math.sin(g))+0.5*Math.random()));
		wholeWorldValue = wholeWorldValue*((growRate-0.015+0.01*Math.random()+0.01*Math.random())/4+0.75);
		world.setWorldAge((double)g); 
		world.setOneAge(oneAge);
		world.setMidAge(midAge); 
		world.setBaseValue(baseValue); 
		baseValue = baseValue+0.1;
		/*if(averageValue/3>baseValue)
			baseValue = averageValue/3;*/
		world.setGrowRate(growRate);
		if(worldValue/30<maxValue)
			growRate = Math.exp(0.99*Math.log(growRate));
		if(worldValue/6000>maxValue)
			growRate = Math.exp(1.05*Math.log(growRate));
		world.setLegacyRate(legacyRate);
		world.setBreedRate(breedRate);
		world.setWorldCount(worldCount);
		world.time(ZonedDateTime.now());
		worldService.save(world);
	}
	
}
