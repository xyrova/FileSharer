const Header = () => {
  const styles = {
    color: "#ffffff", // White text
    fontWeight: 500,
    fontSize: "1rem", // Adjust font size
    textAlign: "left", // Center the text
    padding: "10px", // Add padding for space
    textShadow: "2px 2px 5px rgba(0, 0, 0, 0.5)", // Add a subtle text shadow
    backgroundImage: "linear-gradient(to right, #4f46e5, #38bdf8)", // Gradient background for the text container
  };

  return (
    <div className="bg-blue-100 h-auto" style={styles}>
      FileSharer.com
    </div>
  );
};

export default Header;
