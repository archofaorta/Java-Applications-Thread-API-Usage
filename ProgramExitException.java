/**
 * 
 * User Defined Exception Class. Thrown By Control & Check Classes
 *
 */
public class ProgramExitException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Class Constructor
	 * @param message
	 */
	public ProgramExitException(String message, int exitCode) {
		super(message);
		this.setExitCode(exitCode);
	}
	
	/**
	 * Set Exit Status 
	 */
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	
	/**
	 * Get Exit Status
	 */
	public int getExitCode() {
		return this.exitCode;
	}

	private int exitCode;
}
