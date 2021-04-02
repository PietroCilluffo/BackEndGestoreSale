package com.progetto.gestore.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import com.progetto.gestore.dto.InfoPrenArduinoDto;
import com.progetto.gestore.dto.PrenotazioneDto;
import org.modelmapper.ModelMapper;
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

	@Autowired private PrenotazioneRepository pRepo;
	@Autowired private ModelMapper modelMapper;
	@Autowired private Environment env;

	@Override
	public List<Prenotazione> getAllPrenotazioni() {
		return pRepo.findAll();
	}

	@Override
	public void AggiungiPrenotazione(PrenotazioneDto pr) throws AddressException, MessagingException, IOException {

		System.out.println(pr.getStanzaDto().getId());
		Prenotazione p = modelMapper.map(pr,Prenotazione.class);
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

	}

	@Override
	public void DelByToken(String token) {
		pRepo.delByToken(token);

	}

	@Override
	public PrenotazioneDto getByToken(String token) {
		Prenotazione p = pRepo.getByToken(token);

		PrenotazioneDto pr = modelMapper.map(p, PrenotazioneDto.class);
		return pr;
	}

	@Override
	public InfoPrenArduinoDto getPrenotazioneAttuale(Date giorno, Date ora, String stanza) {
		Prenotazione p = pRepo.getPrenotazioneAttualeByStanza(giorno, ora, stanza);
		InfoPrenArduinoDto pr = modelMapper.map(p,InfoPrenArduinoDto.class);
		return pr;
	}

	@Override
	public InfoPrenArduinoDto getPrenotazioneSuccessiva(Date giorno, Date ora, String stanza) {
		Prenotazione p = pRepo.getPrenotazioniSuccessiveByStanza(giorno, ora, stanza).get(0);
		InfoPrenArduinoDto pr = modelMapper.map(p,InfoPrenArduinoDto.class);
		return pr;
	}

	@Override
	public List<PrenotazioneDto> getPrenotazioniBySettimanaStanza(String nome) {
		List<Prenotazione> settimana = new ArrayList();
		List<PrenotazioneDto> prenotazioneDtos = new ArrayList<>();
		Date now = new Date();
		List<Prenotazione> prenotazioneList = pRepo.getByStanzaGiorno(nome, now);
		for (int i = 0; i<6 ; i++){
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			c.add(Calendar.DATE, 1);
			now = c.getTime();
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
	public List<PrenotazioneDto> getPrenotazioniByGiornoStanza(String nome, Date giorno) {
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
		msg.setContent("http://frontendIp:porta/annullamento/" + token, "text/html");
		msg.setSentDate(new Date());




		Transport.send(msg);
	}

}
