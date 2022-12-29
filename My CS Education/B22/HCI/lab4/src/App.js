import logo from './logo.svg';
import './App.css';
import { Button, TextField, Grid, Box } from '@mui/material';

function App() {
  return (
    <div className="App">
      <Grid container spacing={2}>
        <Grid item xs={12} justifyContent="center" alignItems="center" backgroundColor="lightBlue">
          <h1 style={{ color: 'white' }}>Human Computer Interaction Newsletter</h1>
        </Grid>
        <Grid item xs={8} display="flex" justifyContent="center" alignItems="center">
          <p>Signup for the human computer interaction newsletter!</p>
          <TextField id="outlined-basic" type="email" label="email@wpi.edu" variant="outlined" style={{ marginRight: '20px' }} />
          <Button variant='contained' onClick={Popup}>Sign Up</Button>
        </Grid>
        <Grid item xs={4} display="flex" justifyContent="center" alignItems="center">
          <Box
            component="img"
            sx={{
              height: '100%',
              width: '100%',
            }}
            alt="A react logo"
            src={logo}
          />
        </Grid>
      </Grid>
    </div>
  );
}
function Popup() {
  alert('Signup successful!')
}

export default App;
