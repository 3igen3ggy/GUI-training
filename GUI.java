package nauka;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class GUI implements ActionListener {
	
	private static JLabel label1;
	private static JLabel label2;
	private static JTextField latitude;	
	private static JTextField longitude;
	private static JLabel daytext;
	private static JLabel monthtext;
	private static JLabel yeartext;
	private static JButton button;	
	private static JTextField dayt;	
	private static JTextField montht;	
	private static JTextField yeart;	
	private static JTextArea wynik;
	private static JTextField gmtt;
	private static JLabel gmttext;

	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		frame.setSize(450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(panel);
		
		panel.setLayout(null);
		
		label1 = new JLabel("Latitude");
		label1.setBounds(10, 20, 80, 25);
		panel.add(label1);
		
		latitude = new JTextField(10);
		latitude.setBounds(100, 20, 165, 25);
		panel.add(latitude);
		
		label2 = new JLabel("Longitude");
		label2.setBounds(10, 50, 80, 25);
		panel.add(label2);
		
		longitude = new JTextField(10);
		longitude.setBounds(100, 50, 165, 25);
		panel.add(longitude);
		
		button = new JButton("Calculate");
		button.setBounds(10, 170, 100, 25);
		button.addActionListener(new GUI());
		panel.add(button);
		
		daytext = new JLabel("Day");
		daytext.setBounds(10, 80, 80, 25);
		panel.add(daytext);
		
		monthtext = new JLabel("Month");
		monthtext.setBounds(10, 110, 80, 25);
		panel.add(monthtext);
		
		yeartext = new JLabel("Year");
		yeartext.setBounds(10, 130, 80, 50);
		panel.add(yeartext);		
		
		dayt = new JTextField(4);
		dayt.setBounds(100, 80, 20, 20);
		panel.add(dayt);

		montht = new JTextField(6);
		montht.setBounds(100, 110, 20, 20);
		panel.add(montht);
		
		yeart = new JTextField(6);
		yeart.setBounds(100, 145, 50, 20);
		panel.add(yeart);
		
		wynik = new JTextArea();
		wynik.setBounds(10, 200, 410, 380);
		panel.add(wynik);
		
		gmttext = new JLabel("GMT");
		gmttext.setBounds(180, 130, 80, 50);
		panel.add(gmttext);		
		
		gmtt = new JTextField(4);
		gmtt.setBounds(215, 145, 50, 20);
		panel.add(gmtt);
		
		wynik.setText("Zelow coordinates are: 51.47, 19.21 \nGMT for Poland is:\n+2 during summer (CEST) \n+1 during winter (CET)");
		
		frame.setVisible(true);
		
		
	}
	public static double anglecheck(double angle) {
		while (angle > 360) {
			angle -= 360;
		}
		while (angle < 0) {
			angle += 360;
		}
		return angle;
	}
	
	public static double hourcheck(double hour) {
		while (hour > 24) {
			hour -= 24;
		}
		while (hour < 0) {
			hour += 24;
		}
		return hour;
	}
	
	public static double[] hdectohms(double angle) {
		double[] arr = new double[3];
		double h = Math.floor(angle);
		double m = Math.floor((angle - h) * 60);
		double s = (((angle - h) * 60) - m) * 60;
		arr[0] = h;
		arr[1] = m;
		arr[2] = s;
		return arr;
	}
	
	public void calcJ(double lat, double lon, int dayt, int montht, int yeart, int gmtt) {
		int days;
		double rest = 0;
		/////////////////////////////////INPUT

		if (lat > 90 || lat < -90) {
			wynik.setText("*ERROR*");
		}

		if (lon > 180 || lon < -180) {
			wynik.setText("*ERROR*");
		}

		if (montht > 12 || montht < 1) {
			wynik.setText("*ERROR*");
		}
		
		if (dayt > 31 || dayt < 1) {
			wynik.setText("*ERROR*");
		}
		
		if (montht == 2 || dayt > 29) {
			wynik.setText("*ERROR*");
		}
		
		//leap year
		int p400 = yeart % 400;
		int p100 = yeart % 100;
		int p4 = yeart % 4;
		
		if (p400 == 0) {
			days = 366;
		} else if (p4 == 0 && p100 != 0) {
			days = 366;
		} else {
			days = 365;
		}
		
		//error if you try to fool the program with 29.02 on non-leap year
		if (montht == 2 && days == 365 && dayt == 29) {
			wynik.setText("*ERROR*");
		}

		//months for leap, non-leap years
		
		int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int[] monthsl = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};		
		
		int i = 1;
		int sum = 0;
		
		if (days == 365) {
			while (i < montht) {
				sum += months[i - 1];
				i++;
			}
			sum += dayt;
			rest = days - sum;
			String dayofyear = (yeart + "." + montht + "." + dayt + " is a " + sum + " day of the year. Till the end of year there is " + rest + " days left.");
		}
		
		if (days == 366) {
			while (i < montht) {
				sum += monthsl[i - 1];
				i++;
			}
			sum += dayt;
			rest = days - sum;
			String dayofyear = (yeart + "." + montht + "." + dayt + " is a " + sum + " day of the year. Till the end of year there is " + rest + " days left.");

		}
		
		//days till 01.01.2000
		
		double dist = 0;
		
		if (yeart >= 2000) {
			int yg = yeart - 1;
			int yd = 2000;
			while (yg >= 2000) {
				p400 = yg % 400;
				p100 = yg % 100;
				p4 = yg % 4;
				if (p400 == 0) {
					days = 366;
				} else if (p4 == 0 && p100 != 0) {
					days = 366;
				} else {
					days = 365;
				}
			dist += days;
			yg--;
			}
		dist += sum - 1;
		
		}
		
		if (yeart < 2000) {
			int yg = 2000;
			int yd = yeart + 1;
			while (yd < 2000) {
				p400 = yd % 400;
				p100 = yd % 100;
				p4 = yd % 4;
				if (p400 == 0) {
					days = 366;
				} else if (p4 == 0 && p100 != 0) {
					days = 366;
				} else {
					days = 365;
				}
			dist += days;
			yd++;
			}
		dist -= rest + 1;
		}

		//ACTUAL PROGRAM FROM WIKI.ORG
		double n = dist + 8e-4;
		
		//Mean Solar noon
		double J = (double) n - lon / 360.0;
		
		//Mean Solar anomaly
		double M = (357.5291 + 0.98560028 * J) % 360;
		
		//Equation of center
		double C = 1.9148 * Math.sin(Math.toRadians(M)) * 2e-2 * Math.sin(Math.toRadians(2 * M)) + 3e-4 * Math.sin(Math.toRadians(3 * M));
		
		//Ecliptic longitude
		double lamb = (M + C + 180 + 102.9372) % 360;
		
		//Solar transit
		double Jstardec =  J + 5318e-6 * Math.sin(Math.toRadians(M)) - 685e-5 * Math.sin(Math.toRadians(2 * lamb));

		Jstardec = Jstardec - Math.floor(Jstardec) - 0.5;
		Jstardec = Jstardec * 24 + gmtt;
		double[] Jstar = hdectohms(hourcheck(Jstardec));

		//Sun declination
		double sindelta = Math.sin(Math.toRadians(lamb)) * Math.sin(Math.toRadians(23.44));
		double delta = Math.toDegrees(Math.asin(sindelta));		
		
		//Hour angle for sunrise / sunset
		double cosc = (double) (Math.sin(Math.toRadians(-0.833)) - Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(delta))) / (Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(delta)));
		double c = Math.toDegrees(Math.acos(cosc));
		double Jrisedec = Jstardec - c / 15.0;
		double Jsetdec = Jstardec + c / 15.0;
				
		//Hour angle for civil sunrise / sunset
		double cosc1 = (double) (Math.sin(Math.toRadians(-6)) - Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(delta))) / (Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(delta)));
		double c1 = Math.toDegrees(Math.acos(cosc1));
		double Jrisedec1 = Jstardec - c1 / 15.0;
		double Jsetdec1 = Jstardec + c1 / 15.0;
		
		//Hour angle for nautical sunrise / sunset
		double cosc2 = (double) (Math.sin(Math.toRadians(-12)) - Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(delta))) / (Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(delta)));
		double c2 = Math.toDegrees(Math.acos(cosc2));
		double Jrisedec2 = Jstardec - c2 / 15.0;
		double Jsetdec2 = Jstardec + c2 / 15.0;
		
		//Hour angle for astronomical sunrise / sunset
		double cosc3 = (double) (Math.sin(Math.toRadians(-18)) - Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(delta))) / (Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(delta)));
		double c3 = Math.toDegrees(Math.acos(cosc3));
		double Jrisedec3 = Jstardec - c3 / 15.0;
		double Jsetdec3 = Jstardec + c3 / 15.0;
		
		//adding results to an array
		double[] Jrise = hdectohms(hourcheck(Jrisedec));
		double[] Jset = hdectohms(hourcheck(Jsetdec));

		double[] Jrise1 = hdectohms(hourcheck(Jrisedec1));
		double[] Jset1 = hdectohms(hourcheck(Jsetdec1));
		
		double[] Jrise2 = hdectohms(hourcheck(Jrisedec2));
		double[] Jset2 = hdectohms(hourcheck(Jsetdec2));
		
		double[] Jrise3 = hdectohms(hourcheck(Jrisedec3));
		double[] Jset3 = hdectohms(hourcheck(Jsetdec3));
		
		double daylength = Jsetdec - Jrisedec;
		double nightlength = 24 - daylength;
		
		if (cosc < -1) {
			daylength = 24;
			nightlength = 0;
		}
		
		if (cosc > 1) {
			daylength = 0;
			nightlength = 24;
		}
		
		double alt = 90 - Math.abs(lat - delta);
	
		String daysto = "asd";
		if (yeart < 2000) {
			daysto = ("Days till 1.1.2000:  " + dist + "\n");
		} 
		if (yeart >= 2000) {
			daysto = ("Days after 1.1.2000:  " + Math.abs(dist) + "\n");
		}
		
		String rise3 = ((int) Jrise3[0] + " : " + (int) Jrise3[1] + " : " + String.format("%1.1f", Jrise3[2]) + " ...... Astronomical sunrise" + "\n");
		String rise2 = ((int) Jrise2[0] + " : " + (int) Jrise2[1] + " : " + String.format("%1.1f", Jrise2[2]) + " ...... Nautical sunrise" + "\n");
		String rise1 = ((int) Jrise1[0] + " : " + (int) Jrise1[1] + " : " + String.format("%1.1f", Jrise1[2]) + " ...... Civil sunrise" + "\n");
		String rise = ("\t" + (int) Jrise[0] + " : " + (int) Jrise[1] + " : " + String.format("%1.1f", Jrise[2]) + " ...... Sunrise time" + "\n");
		String Jstars = ("\t" + (int) Jstar[0] + " : " + (int) Jstar[1] + " : " + String.format("%1.1f", Jstar[2]) + " ...... Transit time, at alt: " + String.format("%1.2f", alt) + " deg."+ "\n");
		String set = ("\t" + (int) Jset[0] + " : " + (int) Jset[1] + " : " + String.format("%1.1f", Jset[2]) + " ...... Sunset time" + "\n");
		String set1 = ((int) Jset1[0] + " : " + (int) Jset1[1] + " : " + String.format("%1.1f", Jset1[2]) + " ...... Civil sunset" + "\n");
		String set2 = ((int) Jset2[0] + " : " + (int) Jset2[1] + " : " + String.format("%1.1f", Jset2[2]) + " ...... Nautical sunset" + "\n");
		String set3 = ((int) Jset3[0] + " : " + (int) Jset3[1] + " : " + String.format("%1.1f", Jset3[2]) + " ...... Astronomical sunset" + "\n");
		String additional = ("\n" + "Daylength: " + String.format("%2.2f", daylength) + "h, Nightlength: " + String.format("%2.2f", nightlength) + "h, Day%: " +
		String.format("%2.2f", (daylength / 24.0 * 100)) + ", Night%: " + String.format("%2.2f", (nightlength / 24.0 * 100)) + "\n"
		+ "");
			
		if (cosc3 < -1 || cosc3 > 1) {
			rise3 = ("-- : -- : --" + " ...... Astronomical sunrise" + "\n");
			set3 = ("-- : -- : --" + " ...... Astronomical sunset" + "\n");
		}
		
		if (cosc2 < -1 || cosc2 > 1) {
			rise2 = ("-- : -- : --" + " ...... Nautical sunrise" + "\n");
			set2 = ("-- : -- : --" + " ...... Nautical sunset" + "\n");
		}
		
		if (cosc1 < -1 || cosc1 > 1) {
			rise1 = ("-- : -- : --" + " ...... Civil sunrise" + "\n");
			set1 = ("-- : -- : --" + " ...... Civil sunset" + "\n");
		}
		
		if (cosc < -1 || cosc > 1) {
			rise = ("\t -- : -- : --" + " ...... Sunrise time" + "\n");
			set = ("\t -- : -- : --" + " ...... Sunset time" + "\n");
		}
		
		double EOT = (5318e-6 * Math.sin(Math.toRadians(M)) - 685e-5 * Math.sin(Math.toRadians(2 * lamb)));
		double eotm = (int) Math.floor(EOT * 24 * 60);
		double eots = (EOT * 24 * 60 - eotm) * 60;
		
		String MSN = ("Mean Solar noon: " + String.format("%2.5f", J) + "\n");
		String MSA = ("Mean Solar anomaly: " + String.format("%2.5f", M) + "\n");
		String EoC = ("Equation of center: " + String.format("%2.5f", C) + "\n");
		String EL = ("Ecliptic longitude: " + String.format("%2.5f", lamb) + "\n");
		String EoT = ("Equation of time: " + eotm + "m, " + String.format("%2.2f", eots) + "s" + "\n");
		
		wynik.setText(rise3 + rise2 + rise1 + rise + Jstars + set + set1 + set2 + set3 + additional + daysto + MSN + MSA + EoC + EL + EoT);;
	}

	@Override
  		public void actionPerformed(ActionEvent arg0) {
 		double lat = Double.parseDouble(latitude.getText());
 		double lon = Double.parseDouble(longitude.getText());
 		int day = Integer.parseInt(dayt.getText());
 		int month = Integer.parseInt(montht.getText());
 		int year = Integer.parseInt(yeart.getText());	
		int gmt = Integer.parseInt(gmtt.getText());

 		calcJ(lat, lon, day, month, year, gmt);
	}
}
