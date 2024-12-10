package models;

public class Claim {
	private String id;
    private String policyId;
    private String description;
    private String status;
    private double amount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Claim(String id, String policyId, String description, String status, double amount) {
		super();
		this.id = id;
		this.policyId = policyId;
		this.description = description;
		this.status = status;
		this.amount = amount;
	}
	public Claim() {
		super();
	}
	@Override
	public String toString() {
		return "Claim [id=" + id + ", policyId=" + policyId + ", description=" + description + ", amount=" + amount
				+ "]";
	}
    
    
}
