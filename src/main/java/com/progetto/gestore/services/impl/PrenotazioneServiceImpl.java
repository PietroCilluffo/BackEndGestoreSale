package com.progetto.gestore.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import com.progetto.gestore.dto.InfoPrenArduinoDto;
import com.progetto.gestore.dto.PrenotazioneDto;
import com.progetto.gestore.repositories.StanzaRepository;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.progetto.gestore.enties.Prenotazione;
import com.progetto.gestore.enties.Stanza;
import com.progetto.gestore.repositories.PrenotazioneRepository;
import com.progetto.gestore.services.PrenotazioneService;

@Service
@Transactional
public class PrenotazioneServiceImpl implements PrenotazioneService {
	private static final Logger logger = LoggerFactory.getLogger(PrenotazioneServiceImpl.class);
	@Autowired private PrenotazioneRepository pRepo;
	@Autowired private ModelMapper modelMapper;
	@Autowired private Environment env;

	@Autowired private StanzaRepository stanzaRepository;
	@Override
	public List<Prenotazione> getAllPrenotazioni() {
		return pRepo.findAll();
	}

	@Override
	public boolean AggiungiPrenotazione(PrenotazioneDto pr) throws AddressException, MessagingException, IOException {


		Prenotazione p = modelMapper.map(pr,Prenotazione.class);

        List<Prenotazione> prenotazioneList = pRepo.getPrenotazioneByStanzaId(pr.getStanzaDto().getId());
        for (Prenotazione prenotazione: prenotazioneList){
        	if(pr.getData().equals(prenotazione.getData())){
				if(pr.getOraInizio().equals(prenotazione.getOraInizio())){

					return false;
				}
				if(pr.getOraInizio().isAfter(prenotazione.getOraInizio())&& pr.getOraInizio().isBefore(prenotazione.getOraInizio())){
					return false;
				}

				if(pr.getOraFine().isAfter(prenotazione.getOraInizio())&& pr.getOraFine().isBefore(prenotazione.getOraFine())){
					return false;
				}
			}

		}

		int len = 10;
		String charsCaps="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Chars="abcdefghijklmnopqrstuvwxyz";
		String nums="0123456789";
		String symbols="!@#$%^&*()_+-=.,/';:?><~*/-+";
		String passSymbols=charsCaps + Chars + nums + symbols;
		Random rnd=new Random();
		char[] token=new char[len];

		for(int i=0; i<len;i++){
			token[i]= passSymbols.charAt(rnd.nextInt(passSymbols.length()));
		}
		String stringToken;

		stringToken = token.toString().substring(2);
		System.out.println(token);
		System.out.println(stringToken);
		p.setDeleteToken(stringToken);
		pRepo.save(p);
		sendmail(p.getEmail(),p.getDeleteToken());
		return  true;

	}

	@Override
	public void DelByToken(String token) {
		pRepo.delByToken(token);

	}

	@Override
	public PrenotazioneDto getByToken(String token) {
		Prenotazione p = pRepo.getByToken(token);
        if(p == null){
        	Prenotazione p1 = new Prenotazione();
			PrenotazioneDto pr = modelMapper.map(p1, PrenotazioneDto.class);
			return pr;
		}
		PrenotazioneDto pr = modelMapper.map(p, PrenotazioneDto.class);
		return pr;
	}

	@Override
	public InfoPrenArduinoDto getPrenotazioneAttuale(LocalDate giorno, LocalTime ora, String arduinoId) {



		Stanza s = stanzaRepository.getStanzaByArduinoId(arduinoId);
		Prenotazione prenotazione = new Prenotazione();

		if(s == null){
			InfoPrenArduinoDto pr = modelMapper.map(prenotazione,InfoPrenArduinoDto.class);
			return pr;
		}


	     List<Prenotazione> p = pRepo.getPrenotazioneByStanzaId(s.getId());

	     for(Prenotazione pr: p){

	     	if((pr.getData().equals(giorno))&& ora.isAfter(pr.getOraInizio()) && ora.isBefore(pr.getOraFine())){

	     		prenotazione = pr;
	     		break;
			}
		 }


		InfoPrenArduinoDto pr = modelMapper.map(prenotazione,InfoPrenArduinoDto.class);
	     if(prenotazione == null) {
			 String crop = pr.getDescrizione();
			 crop = crop.substring(0, Math.min(crop.length(), 48));
			 if (crop.length() > 47) {
				 crop = crop + "..";
			 }

			 pr.setDescrizione(crop);
		 }
		return pr;
	}

	@Override
	public InfoPrenArduinoDto getPrenotazioneSuccessiva(LocalDate giorno, LocalTime ora, String arduinoId) {

		Stanza s = stanzaRepository.getStanzaByArduinoId(arduinoId);
		Prenotazione prenotazione = new Prenotazione();
		if(s == null){
			InfoPrenArduinoDto pr = modelMapper.map(prenotazione,InfoPrenArduinoDto.class);
			return pr;
		}
		List<Prenotazione> p = pRepo.getPrenotazioneByStanzaId(s.getId());
		if(p.isEmpty()){
			InfoPrenArduinoDto pr = modelMapper.map(prenotazione,InfoPrenArduinoDto.class);
			return pr;
		}
		List <Prenotazione> temp = new ArrayList<>();

		for(Prenotazione pr: p){


			if((pr.getData().equals(giorno)) && ora.isBefore(pr.getOraInizio())){
				temp.add(pr);
			}
		}
		if(temp.isEmpty()){
			InfoPrenArduinoDto pr = modelMapper.map(prenotazione,InfoPrenArduinoDto.class);
			return pr;
		}
		prenotazione = temp.get(0);
		for (Prenotazione pr : temp){
			if(prenotazione.getOraInizio().isAfter(pr.getOraInizio())){

				prenotazione = pr;
			}
		}

		InfoPrenArduinoDto pr = modelMapper.map(prenotazione,InfoPrenArduinoDto.class);
		String crop = pr.getDescrizione();
		crop = crop.substring(0,Math.min(crop.length(),48));

		if(crop.length() > 47){
			crop = crop + "..";
		}
		pr.setDescrizione(crop);
		return pr;
	}

	@Override
	public List<PrenotazioneDto> getPrenotazioniBySettimanaStanza(String nome) {
		List<Prenotazione> settimana = new ArrayList();
		List<PrenotazioneDto> prenotazioneDtos = new ArrayList<>();
		LocalDate now = LocalDate.now();

		List<Prenotazione> prenotazioneList = pRepo.getByStanzaGiorno(nome, now);

		for (int i = 0; i<6 ; i++){
			now = now.plusDays(1);
			List<Prenotazione> prenotazioneListtemp = pRepo.getByStanzaGiorno(nome, now);

			prenotazioneList.addAll(prenotazioneListtemp);
		}
		for(Prenotazione p : prenotazioneList){


			PrenotazioneDto pr = modelMapper.map(p, PrenotazioneDto.class);
			prenotazioneDtos.add(pr);
		}

		return prenotazioneDtos;

	}

	@Override
	public List<PrenotazioneDto> getPrenotazioniByGiornoStanza(String nome, LocalDate giorno) {
		List<Prenotazione> prenotazioneList = pRepo.getByStanzaGiorno(nome, giorno);
		List<PrenotazioneDto> prenotazioneDtos = new ArrayList<>();

		for(Prenotazione p : prenotazioneList){


			PrenotazioneDto pr = modelMapper.map(p, PrenotazioneDto.class);
			
			prenotazioneDtos.add(pr);
		}
		return prenotazioneDtos;
	}


	private void sendmail(String mail, String token) throws AddressException, MessagingException, IOException{

		String ip = env.getProperty("sede.ip");
		String ipFrontEnd = env.getProperty("sede.frontendIp");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("si2001gestore@gmail.com", "celafaremo");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("si2001gestore@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject("Prenotazione Stanza");
		msg.setContent("http://"+ipFrontEnd+ "/prenotazione/deleteByToken/" + token, "text/html");
		msg.setSentDate(new Date());




		Transport.send(msg);
	}

}
