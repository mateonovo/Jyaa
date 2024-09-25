import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './components/users/register.component';
import { UserDetailComponent } from './components/users/userDetail.component';
import { EditUserComponent } from './components/users/edit-user.component';
import { DeleteUserComponent } from './components/users/delete-user.component';
import { UserListComponent } from './components/users/user-list.component';
import { FamiliaListComponent } from './components/familiasProductoras/familia-list.component';
import { RegisterFamiliaComponent } from './components/familiasProductoras/registerFamilia.component';
import { EditFamiliaComponent } from './components/familiasProductoras/edit-familia.component';
import { DeleteFamiliaComponent } from './components/familiasProductoras/delete-familia.component';
import { LoteListComponent } from './components/lotes/lote-list.component';
import { CreateProductComponent } from './components/productoElaborado/create-product.component';
import { ProductListComponent } from './components/productoElaborado/list-products.component';
import { InsumoListComponent } from './components/insumos/insumo-list.component';
import { RegisterInsumoComponent } from './components/insumos/registerInsumo.component';
import { EditInsumoComponent } from './components/insumos/edit-insumo.component';
import { DeleteInsumoComponent } from './components/insumos/delete-insumo.component';
import {CanalListComponent} from './components/canales/canal-list.component';
import {DeleteCanalComponent} from './components/canales/delete-canal.component';
import {EditCanalComponent} from './components/canales/edit-canal.component';
import {CreateCanalComponent} from './components/canales/create-canal.component';
import { RecetaListComponent } from './components/recetas/receta-list.component';
import { RegisterRecetaComponent } from './components/recetas/registerReceta.component';
import { RecetaDetailComponent } from './components/recetas/receta-detail.component';
import { EditRecetaComponent } from './components/recetas/edit-receta.component';
import { DeleteRecetaComponent } from './components/recetas/delete-receta.component';
//import { AgregarInsumosComponent } from './components/productoElaborado/add-supplies.component';
import {MateriaListComponent} from './components/MateriasPrimas/materia-list.component';
import {MateriaDeleteComponent} from './components/MateriasPrimas/delete-materia.component';
import {EditMateriaComponent} from './components/MateriasPrimas/edit-materia.component';
import {CreateMateriaComponent} from './components/MateriasPrimas/create-materia.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './auth.guard';
import { LoteDetailComponent } from './components/lotes/lote-detail.component';
import { DeleteLoteComponent } from './components/lotes/delete-lote.component';
import { RegisterLoteComponent } from './components/lotes/register-lote.component';
import { ProductoDetailComponent } from './components/productoElaborado/producto-detail.component';
import { DeleteProductoComponent } from './components/productoElaborado/delete-producto.component';
import { EntregarProductoComponent } from './components/productoElaborado/deliver-product.component';
import { CanalProductsComponent } from './components/canales/canal-products.component';

export const routes: Routes = [
  
  { path: '', component: HomeComponent },
  
  //Usuarios
  { path: 'users', component: UserListComponent,canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'user/:id', component: UserDetailComponent ,canActivate: [AuthGuard] },
  { path: 'edit-user/:id', component: EditUserComponent ,canActivate: [AuthGuard] },
  { path: 'delete-user/:id', component: DeleteUserComponent ,canActivate: [AuthGuard]}, 
  
  //Familias
  { path: 'familias', component: FamiliaListComponent,canActivate: [AuthGuard] },
  { path: 'registerFamilia', component: RegisterFamiliaComponent,canActivate: [AuthGuard] },
  { path: 'edit-familia/:id', component: EditFamiliaComponent , canActivate: [AuthGuard]},
  { path: 'delete-familia/:id', component: DeleteFamiliaComponent , canActivate: [AuthGuard]},
  
  //Insumos
  { path: 'insumos', component: InsumoListComponent , canActivate: [AuthGuard]},
  { path: 'registerInsumo', component: RegisterInsumoComponent , canActivate: [AuthGuard]},
  { path: 'edit-insumo/:id', component: EditInsumoComponent , canActivate: [AuthGuard]},
  { path: 'delete-insumo/:id', component: DeleteInsumoComponent, canActivate: [AuthGuard] },
  //{ path: 'agregarInsumos/:productId', component: AgregarInsumosComponent , canActivate: [AuthGuard]},

  //Lotes
  { path: 'lotes', component: LoteListComponent, canActivate: [AuthGuard] },
  { path: 'lote/:id', component: LoteDetailComponent, canActivate: [AuthGuard] },
  { path: 'delete-lote/:id', component: DeleteLoteComponent, canActivate: [AuthGuard] },
  { path: 'agregarLote', component: RegisterLoteComponent, canActivate: [AuthGuard] },
  
  //Receta
  { path: 'recetas', component: RecetaListComponent, canActivate: [AuthGuard] },
  { path: 'registerReceta', component: RegisterRecetaComponent , canActivate: [AuthGuard]},
  { path: 'receta/:id', component: RecetaDetailComponent, canActivate: [AuthGuard] },
  { path: 'edit-receta/:id', component: EditRecetaComponent, canActivate: [AuthGuard] },
  { path: 'delete-receta/:id', component: DeleteRecetaComponent, canActivate: [AuthGuard] }, 
 
  //Productos
  { path: 'productos', component: ProductListComponent, canActivate: [AuthGuard]},
  { path: 'crearProducto/:id', component: CreateProductComponent, canActivate: [AuthGuard]},
  { path: 'producto/:id', component: ProductoDetailComponent, canActivate: [AuthGuard]},
  { path: 'delete-producto/:id', component: DeleteProductoComponent, canActivate: [AuthGuard]},
  {path: 'entregarACanal/:productoId', component: EntregarProductoComponent, canActivate: [AuthGuard]},

  //Canales
  {path: 'canales', component: CanalListComponent, canActivate: [AuthGuard]},
  {path: 'delete-canal/:id', component: DeleteCanalComponent, canActivate: [AuthGuard]},
  {path: 'edit-canal/:id', component: EditCanalComponent, canActivate: [AuthGuard]},
  {path: 'create-canal', component: CreateCanalComponent, canActivate: [AuthGuard]},
  {path: 'canal-productos/:id', component: CanalProductsComponent, canActivate: [AuthGuard]},

  //Materias Primas
  {path: 'materiasPrimas', component: MateriaListComponent, canActivate: [AuthGuard]},
  {path : 'delete-materia/:id', component: MateriaDeleteComponent, canActivate: [AuthGuard]},
  {path: 'edit-materia/:id', component: EditMateriaComponent, canActivate: [AuthGuard]},
  {path: 'create-materia', component: CreateMateriaComponent, canActivate: [AuthGuard]},

  //login
  {path: 'login', component: LoginComponent}

  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
