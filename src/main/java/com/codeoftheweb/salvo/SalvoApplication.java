package com.codeoftheweb.salvo;

;
import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}


		@Bean
		public CommandLineRunner initData(PlayerRepository repository, GameRepository repositoryGame,
										  GamePlayerRepository repositoryGamePlayer, ShipRepository repositoryShip,
										  SalvoRepository repositorySalvo, ScoreRepository repositoryScore) {
			return (args) -> {
				// save a couple of customers

				//instancia
				Player player1 = new Player("j.bauer@hotmail.com", passwordEncoder().encode("abc"));
				Player player2 = new Player("c.obrian@gmail.com",  passwordEncoder().encode("abc1"));
				Player player3 = new Player("kim_bauer@hotmail.com",  passwordEncoder().encode("abc2"));
				Player player4 = new Player("t_almeida@gmail.com",  passwordEncoder().encode("abc3"));

				//registro
				repository.save(player1);
				repository.save(player2);
				repository.save(player3);
				repository.save(player4);

				//instancia
				Game game1 = new Game(LocalDateTime.now().plusHours(1));
				Game game2 = new Game(LocalDateTime.now().plusHours(2));
				Game game3 = new Game(LocalDateTime.now().plusHours(3));
				Game game4 = new Game(LocalDateTime.now().plusHours(4));
				Game game5 = new Game(LocalDateTime.now().plusHours(5));
				Game game6 = new Game(LocalDateTime.now().plusHours(6));
				Game game7 = new Game(LocalDateTime.now().plusHours(7));
				Game game8 = new Game(LocalDateTime.now().plusHours(8));

				//registro
				repositoryGame.save(game1);
				repositoryGame.save(game2);
				repositoryGame.save(game3);
				repositoryGame.save(game4);
				repositoryGame.save(game3);
				repositoryGame.save(game4);
				repositoryGame.save(game5);
				repositoryGame.save(game6);
				repositoryGame.save(game7);
				repositoryGame.save(game8);


				//instancia
				GamePlayer gamePlayer1 = new GamePlayer(game1, player1);
				GamePlayer gamePlayer2 = new GamePlayer(game1, player2);
				GamePlayer gamePlayer3 = new GamePlayer(game2, player1);
				GamePlayer gamePlayer4 = new GamePlayer(game2, player2);
				GamePlayer gamePlayer5 = new GamePlayer(game3, player2);
				GamePlayer gamePlayer6 = new GamePlayer(game3, player4);
				GamePlayer gamePlayer7 = new GamePlayer(game4, player2);
				GamePlayer gamePlayer8 = new GamePlayer(game4, player1);
				GamePlayer gamePlayer9 = new GamePlayer(game5, player4);
				GamePlayer gamePlayer10 = new GamePlayer(game5, player1);
				GamePlayer gamePlayer11 = new GamePlayer(game6, player3);
				GamePlayer gamePlayer12 = new GamePlayer(game6, player3);
				GamePlayer gamePlayer13 = new GamePlayer(game7, player4);
				GamePlayer gamePlayer14 = new GamePlayer(game7, player3);
				GamePlayer gamePlayer15 = new GamePlayer(game8, player3);
				GamePlayer gamePlayer16 = new GamePlayer(game8, player4);

				//registro
				repositoryGamePlayer.save(gamePlayer1);
				repositoryGamePlayer.save(gamePlayer2);
				repositoryGamePlayer.save(gamePlayer3);
				repositoryGamePlayer.save(gamePlayer4);
				repositoryGamePlayer.save(gamePlayer5);
				repositoryGamePlayer.save(gamePlayer6);
				repositoryGamePlayer.save(gamePlayer7);
				repositoryGamePlayer.save(gamePlayer8);
				repositoryGamePlayer.save(gamePlayer9);
				repositoryGamePlayer.save(gamePlayer10);
				repositoryGamePlayer.save(gamePlayer11);
				repositoryGamePlayer.save(gamePlayer12);
				repositoryGamePlayer.save(gamePlayer13);
				repositoryGamePlayer.save(gamePlayer14);
				repositoryGamePlayer.save(gamePlayer15);
				repositoryGamePlayer.save(gamePlayer16);



				List<String> shipLocations1 = new ArrayList<>(Arrays.asList("H2", "H3", "H4"));
				List<String> shipLocations2 = new ArrayList<>(Arrays.asList("E1","F1", "G1"));
				List<String> shipLocations3 = new ArrayList<>(Arrays.asList("B4","B5"));
				List<String> shipLocations4 = new ArrayList<>(Arrays.asList("B5","C5", "D5"));
				List<String> shipLocations5 = new ArrayList<>(Arrays.asList("F1","F2"));
				List<String> shipLocations6 = new ArrayList<>(Arrays.asList("B5","C5", "D5"));
				List<String> shipLocations7 = new ArrayList<>(Arrays.asList("C6","C7"));
				List<String> shipLocations8 = new ArrayList<>(Arrays.asList("A2","A3", "A4"));
				List<String> shipLocations9 = new ArrayList<>(Arrays.asList("G6","H6"));
				List<String> shipLocations10 = new ArrayList<>(Arrays.asList("H2", "G2", "F2"));
				List<String> shipLocations11 = new ArrayList<>(Arrays.asList("C5", "C6"));
				List<String> shipLocations12 = new ArrayList<>(Arrays.asList("A3", "A4", "A5"));
				List<String> shipLocations13 = new ArrayList<>(Arrays.asList("G6", "H6"));
				List<String> shipLocations14 = new ArrayList<>(Arrays.asList("B2", "B4", "B5"));
				List<String> shipLocations15 = new ArrayList<>(Arrays.asList("C9", "C10"));
				List<String> shipLocations16 = new ArrayList<>(Arrays.asList("A1", "A2", "A4"));
				List<String> shipLocations17 = new ArrayList<>(Arrays.asList("G7", "H7"));
				List<String> shipLocations18 = new ArrayList<>(Arrays.asList("B6", "B7", "B8"));
				List<String> shipLocations19 = new ArrayList<>(Arrays.asList("D9", "D10"));
				List<String> shipLocations20 = new ArrayList<>(Arrays.asList("A1", "A2", "A4"));
				List<String> shipLocations21 = new ArrayList<>(Arrays.asList("H7", "I7"));
				List<String> shipLocations22 = new ArrayList<>(Arrays.asList("A1", "A2", "A4"));
				List<String> shipLocations23 = new ArrayList<>(Arrays.asList("H7", "I7"));
				List<String> shipLocations24 = new ArrayList<>(Arrays.asList("D1", "D2", "D4"));
				List<String> shipLocations25 = new ArrayList<>(Arrays.asList("B7", "C7"));
				List<String> shipLocations26 = new ArrayList<>(Arrays.asList("J1", "J2", "J4"));
				List<String> shipLocations27 = new ArrayList<>(Arrays.asList("E7", "F7"));
				List<String> shipLocations28 = new ArrayList<>(Arrays.asList("E10", "F10", "G10"));
				List<String> shipLocations29 = new ArrayList<>(Arrays.asList("D2", "D3"));
				List<String> shipLocations30 = new ArrayList<>(Arrays.asList("A1", "A2", "A4"));
				List<String> shipLocations31 = new ArrayList<>(Arrays.asList("J7", "J8"));

				Ship ship1 = new Ship("destroyer", gamePlayer1, shipLocations1);
				Ship ship2 = new Ship("submarine", gamePlayer1, shipLocations2);
				Ship ship3 = new Ship("patrolboat", gamePlayer1, shipLocations3);
				Ship ship4 = new Ship("destroyer", gamePlayer2, shipLocations4);
				Ship ship5 = new Ship("submarine", gamePlayer2, shipLocations5);
				Ship ship6 = new Ship("destroyer", gamePlayer3, shipLocations6);
				Ship ship7 = new Ship("patrolboat", gamePlayer3, shipLocations7);
				Ship ship8 = new Ship("submarine", gamePlayer4, shipLocations8);
				Ship ship9 = new Ship("patrolboat", gamePlayer4, shipLocations9);
				Ship ship10 = new Ship("destroyer", gamePlayer5, shipLocations10);
				Ship ship11 = new Ship("patrolboat", gamePlayer5, shipLocations11);
				Ship ship12 = new Ship("submarine", gamePlayer6, shipLocations12);
				Ship ship13 = new Ship("patrolboat", gamePlayer6, shipLocations13);
				Ship ship14 = new Ship("destroyer", gamePlayer7, shipLocations14);
				Ship ship15 = new Ship("patrolboat", gamePlayer7, shipLocations15);
				Ship ship16 = new Ship("submarine", gamePlayer8, shipLocations16);
				Ship ship17 = new Ship("patrolboat", gamePlayer8, shipLocations17);
				Ship ship18 = new Ship("destroyer", gamePlayer9, shipLocations18);
				Ship ship19 = new Ship("patrolboat", gamePlayer9, shipLocations19);
				Ship ship20 = new Ship("submarine", gamePlayer10, shipLocations20);
				Ship ship21 = new Ship("patrolboat", gamePlayer10, shipLocations21);
				Ship ship22 = new Ship("destroyer", gamePlayer11, shipLocations22);
				Ship ship23 = new Ship("patrolboat", gamePlayer12, shipLocations23);
				Ship ship24 = new Ship("submarine", gamePlayer13, shipLocations24);
				Ship ship25 = new Ship("patrolboat", gamePlayer13, shipLocations25);
				Ship ship26 = new Ship("destroyer", gamePlayer14, shipLocations26);
				Ship ship27 = new Ship("patrolboat", gamePlayer14, shipLocations27);
				Ship ship28 = new Ship("submarine", gamePlayer15, shipLocations28);
				Ship ship29 = new Ship("patrolboat", gamePlayer15, shipLocations29);
				Ship ship30 = new Ship("destroyer", gamePlayer16, shipLocations30);
				Ship ship31 = new Ship("patrolboat", gamePlayer16, shipLocations31);

				repositoryShip.save(ship1);
				repositoryShip.save(ship2);
				repositoryShip.save(ship3);
				repositoryShip.save(ship4);
				repositoryShip.save(ship5);
				repositoryShip.save(ship6);
				repositoryShip.save(ship7);
				repositoryShip.save(ship8);
				repositoryShip.save(ship9);
				repositoryShip.save(ship10);
				repositoryShip.save(ship11);
				repositoryShip.save(ship12);
				repositoryShip.save(ship13);
				repositoryShip.save(ship14);
				repositoryShip.save(ship15);
				repositoryShip.save(ship16);
				repositoryShip.save(ship17);
				repositoryShip.save(ship18);
				repositoryShip.save(ship19);
				repositoryShip.save(ship20);
				repositoryShip.save(ship21);
				repositoryShip.save(ship22);
				repositoryShip.save(ship23);
				repositoryShip.save(ship24);
				repositoryShip.save(ship25);
				repositoryShip.save(ship26);
				repositoryShip.save(ship27);
				repositoryShip.save(ship28);
				repositoryShip.save(ship29);
				repositoryShip.save(ship30);
				repositoryShip.save(ship31);

				List<String> salvoLocation1 = new ArrayList<>(Arrays.asList("B5","C5","F1"));
				List<String> salvoLocation2 = new ArrayList<>(Arrays.asList("B4", "B5", "B6"));
				List<String> salvoLocation3 = new ArrayList<>(Arrays.asList("F2", "D5"));
				List<String> salvoLocation4 = new ArrayList<>(Arrays.asList("E1","H3","A2"));
				List<String> salvoLocation5 = new ArrayList<>(Arrays.asList("A2","A4","G6"));
				List<String> salvoLocation6 = new ArrayList<>(Arrays.asList("B5", "D5", "C7"));
				List<String> salvoLocation7 = new ArrayList<>(Arrays.asList("A3", "H6"));
				List<String> salvoLocation8 = new ArrayList<>(Arrays.asList("C5","C6"));
				List<String> salvoLocation9 = new ArrayList<>(Arrays.asList("G6", "H6", "A4"));
				List<String> salvoLocation10 = new ArrayList<>(Arrays.asList("A2", "A3","D8"));
				List<String> salvoLocation11 = new ArrayList<>(Arrays.asList("H1","H2", "H3"));
				List<String> salvoLocation12 = new ArrayList<>(Arrays.asList("E1", "F2", "G3"));
				List<String> salvoLocation13 = new ArrayList<>(Arrays.asList("A3", "A4", "F7"));
				List<String> salvoLocation14 = new ArrayList<>(Arrays.asList("A2", "G6","H6"));
				List<String> salvoLocation15 = new ArrayList<>(Arrays.asList("B5","C6", "H1"));
				List<String> salvoLocation16 = new ArrayList<>(Arrays.asList("C5", "C7", "D5"));

				Salvo salvo1 = new Salvo(gamePlayer1, 1, salvoLocation1);
				Salvo salvo2 = new Salvo(gamePlayer2, 1, salvoLocation2);
				Salvo salvo3 = new Salvo(gamePlayer1, 2, salvoLocation3);
				Salvo salvo4 = new Salvo(gamePlayer2, 2, salvoLocation4);

				Salvo salvo5 = new Salvo(gamePlayer3, 1, salvoLocation5);
				Salvo salvo6 = new Salvo(gamePlayer4, 1, salvoLocation6);
				Salvo salvo7 = new Salvo(gamePlayer3, 2, salvoLocation7);
				Salvo salvo8 = new Salvo(gamePlayer4, 2, salvoLocation8);

				Salvo salvo9 = new Salvo(gamePlayer5, 1, salvoLocation9);
				Salvo salvo10 = new Salvo(gamePlayer5, 2, salvoLocation10);
				Salvo salvo11 = new Salvo(gamePlayer6, 1, salvoLocation11);
				Salvo salvo12 = new Salvo(gamePlayer6, 2, salvoLocation12);

				Salvo salvo13 = new Salvo(gamePlayer7, 1, salvoLocation13);
				Salvo salvo14 = new Salvo(gamePlayer7, 2, salvoLocation14);
				Salvo salvo15 = new Salvo(gamePlayer8, 1, salvoLocation15);
				Salvo salvo16 = new Salvo(gamePlayer8, 2, salvoLocation16);

				repositorySalvo.save(salvo1);
				repositorySalvo.save(salvo2);
				repositorySalvo.save(salvo3);
				repositorySalvo.save(salvo4);
				repositorySalvo.save(salvo5);
				repositorySalvo.save(salvo6);
				repositorySalvo.save(salvo7);
				repositorySalvo.save(salvo8);
				repositorySalvo.save(salvo9);
				repositorySalvo.save(salvo10);
				repositorySalvo.save(salvo11);
				repositorySalvo.save(salvo12);
				repositorySalvo.save(salvo13);
				repositorySalvo.save(salvo14);
				repositorySalvo.save(salvo15);
				repositorySalvo.save(salvo16);

				Score score1 = new Score(1.0f, LocalDateTime.now(), game1, player1);
				Score score2 = new Score(0f, LocalDateTime.now(), game1, player2);
				Score score3 = new Score(0.5f, LocalDateTime.now(), game2, player1);
				Score score4 = new Score(0.5f, LocalDateTime.now(), game2, player2);
				Score score5 = new Score(1.0f, LocalDateTime.now(), game3, player2);
				Score score6 = new Score(0f, LocalDateTime.now(), game3, player4);
				Score score7 = new Score(0.5f, LocalDateTime.now(), game4, player2);
				Score score8 = new Score(0.5f, LocalDateTime.now(), game4, player1);
				Score score9 = new Score(1.0f, LocalDateTime.now(), game5, player4);
				Score score10 = new Score(0f, LocalDateTime.now(), game5, player1);
				Score score11 = new Score(0.5f, LocalDateTime.now(), game7, player4);
				Score score12 = new Score(0.5f, LocalDateTime.now(),game7, player3);
				Score score13 = new Score(0f, LocalDateTime.now(), game8, player3);
				Score score14 = new Score(1.0f, LocalDateTime.now(), game8,player4);

				repositoryScore.save(score1);
				repositoryScore.save(score2);
				repositoryScore.save(score3);
				repositoryScore.save(score4);
				repositoryScore.save(score5);
				repositoryScore.save(score6);
				repositoryScore.save(score7);
				repositoryScore.save(score8);
				repositoryScore.save(score9);
				repositoryScore.save(score10);
				repositoryScore.save(score11);
				repositoryScore.save(score12);
				repositoryScore.save(score13);
				repositoryScore.save(score14);

			};

	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}





