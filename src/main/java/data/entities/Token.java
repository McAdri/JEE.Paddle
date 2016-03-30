package data.entities;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;

@Entity
public class Token {

	private static final int HOURS_EXPIRED = 1;
	
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String value;
    
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationTime;

    @ManyToOne
    @JoinColumn
    private User user;

    public Token() {
    }

    public Token(User user) {
        assert user != null;
        this.user = user;
        this.value = new Encrypt().encryptInBase64UrlSafe("" + user.getId() + user.getUsername() + Long.toString(new Date().getTime())
                + user.getPassword());
        
        this.creationTime = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }
    
    public Calendar getCreationTime(){
    	return creationTime;
    }
    
    public void setCreationTime(Calendar creationTime){
    	this.creationTime = creationTime;
    }
    
    public boolean hasExpired(){
    	Calendar nowTime = Calendar.getInstance();
    	Calendar expirationTime = this.creationTime;
    	expirationTime.add(Calendar.HOUR_OF_DAY, HOURS_EXPIRED);
    	return (expirationTime.compareTo(nowTime) <= 0 );
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Token) obj).id;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", value=" + value + ", userId=" + user.getId() + "]";
    }
}
